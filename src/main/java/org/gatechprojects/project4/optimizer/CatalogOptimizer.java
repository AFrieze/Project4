package org.gatechprojects.project4.optimizer;

import java.util.ArrayList;
import java.util.Arrays;
//import java.lang.Object.*;
//import java.lang.String.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gatechprojects.project4.SharedDataModules.InputCourseCompetence;
import org.gatechprojects.project4.SharedDataModules.InputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.InputProfessor;
import org.gatechprojects.project4.SharedDataModules.InputStudent;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.InputTA;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.OutputTACourseAssignment;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
import org.gatechprojects.project4.SharedDataModules.User;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class CatalogOptimizer {

	private static final int DEFAULT_MAX_COURSE_SIZE = 50;
	private Map<String, InputOfferedCourse> courseNameToInputLookup = new HashMap<String, InputOfferedCourse>();
	private Map<String, InputTA> tasNameToInputLookup = new HashMap<String, InputTA>();
	private Map<String, InputStudent> studentsNameToInputLookup = new HashMap<String, InputStudent>();
	private Map<String, InputProfessor> professorNameToInputLookup = new HashMap<String, InputProfessor>();

	public static boolean contains(int[] arr, int targetValue) {
		for (int i : arr) {
			if (i == targetValue)
				return true;
		}
		return false;

	}

	private String[] populateCoursesAndLookup(OptimizerCalculation calculation) {
		String[] courses = new String[calculation.getInputOfferedCourses().size()];
		int i = 0;
		for (InputOfferedCourse offeredCourse : calculation.getInputOfferedCourses()) {
			courses[i] = offeredCourse.getCourse().getName();
			courseNameToInputLookup.put(offeredCourse.getCourse().getName(), offeredCourse);
			i++;
		}
		return courses;
	}

	private String buildTAIdentifier(InputTA ta) {
		return String.format("%s-%s", ta.getUser().getFirstName(), ta.getUser().getLastName());
	}

	private String buildUserIdentifier(User user) {
		return String.format("%s-%s", user.getFirstName(), user.getLastName());
	}

	private String buildProfessorIdentifier(InputProfessor professor) {
		return String.format("%s-%s", professor.getUser().getFirstName(), professor.getUser().getLastName());
	}

	private String buildStudentIdentifier(InputStudent student) {
		return String.format("%s-%s", student.getUser().getFirstName(), student.getUser().getLastName());
	}

	private String[] populateTasAndLookup(OptimizerCalculation calculation) {
		String[] tas = new String[calculation.getInputOfferedCourses().size()];
		int i = 0;
		for (InputTA availableTA : calculation.getInputTAs()) {
			tas[i] = buildTAIdentifier(availableTA);
			tasNameToInputLookup.put(buildTAIdentifier(availableTA), availableTA);
			i++;
		}
		return tas;
	}

	private String[] populateStudentsAndLookup(OptimizerCalculation calculation) {
		String[] students = new String[calculation.getInputStudents().size()];
		int i = 0;
		for (InputStudent student : calculation.getInputStudents()) {
			students[i] = buildStudentIdentifier(student);
			studentsNameToInputLookup.put(buildStudentIdentifier(student), student);
			i++;
		}
		return students;
	}

	private String[] populateProfessorsAndLookup(OptimizerCalculation calculation) {
		String[] professors = new String[calculation.getInputProfessors().size()];
		int i = 0;
		for (InputProfessor availableProfessor : calculation.getInputProfessors()) {
			professors[i] = buildProfessorIdentifier(availableProfessor);
			professorNameToInputLookup.put(buildProfessorIdentifier(availableProfessor), availableProfessor);
			i++;
		}
		return professors;
	}

	private int[][] buildInstructorCompetencies(OptimizerCalculation calculation, String[] courses) {
		List<String> searchableCourses = Arrays.asList(courses);
		int[][] instructorCompetencies = new int[calculation.getInputProfessors().size()][];

		int i = 0;
		for (InputProfessor inputProfessor : calculation.getInputProfessors()) {
			int nbrCompetencies = inputProfessor.getCourseCompetencies().size();
			instructorCompetencies[i] = new int[nbrCompetencies];
			for (int j = 0; j < nbrCompetencies; j++) {
				InputCourseCompetence competence = inputProfessor.getCourseCompetencies().get(j);
				int courseIndex = searchableCourses.indexOf(competence.getCourse().getName());
				instructorCompetencies[i][j] = courseIndex;
			}
			i++;
		}
		return instructorCompetencies;
	}

	private int[][] buildInstructorCourseAssignments(OptimizerCalculation calculation, String[] courses,
			String[] professors) {
		List<String> searchableCourses = Arrays.asList(courses);
		List<String> searchableProfessors = Arrays.asList(professors);
		List<Integer[]> assignments = new ArrayList<Integer[]>();
		for (InputOfferedCourse offeredCourse : calculation.getInputOfferedCourses()) {
			if (offeredCourse.getAssignedProfessor() != null) {
				int professorIndex = searchableProfessors
						.indexOf(this.buildUserIdentifier(offeredCourse.getAssignedProfessor()));
				int courseIndex = searchableCourses.indexOf(offeredCourse.getCourse().getName());
				assignments.add(new Integer[] { professorIndex, courseIndex });
			}
		}
		int[][] professorCourseAssignments = new int[assignments.size()][];
		int i = 0;
		for (Integer[] assignment : assignments) {
			professorCourseAssignments[i] = new int[] { assignment[0], assignment[1] };
			i++;
		}
		return professorCourseAssignments;
	}

	private int[] buildCreditsCompleted(OptimizerCalculation calculation) {
		int[] creditsCompleted = new int[calculation.getInputStudents().size()];
		int i = 0;
		for (InputStudent student : calculation.getInputStudents()) {
			creditsCompleted[i] = student.getCreditsTaken();
			i++;
		}
		return creditsCompleted;
	}

	private int[] buildMaximumDesiredCourseLoad(OptimizerCalculation calculation) {
		int[] maxCourses = new int[calculation.getInputStudents().size()];
		int i = 0;
		for (InputStudent student : calculation.getInputStudents()) {
			maxCourses[i] = student.getNbrCoursesDesired();
			i++;
		}
		return maxCourses;
	}

	private int[][] buildRequestedCourses(OptimizerCalculation calculation, String[] courses) {
		int[][] requestedCourses = new int[calculation.getInputStudents().size()][];
		List<String> searchableCourses = Arrays.asList(courses);
		int i = 0;
		for (InputStudent inputStudent : calculation.getInputStudents()) {
			int nbrPreferences = inputStudent.getCoursePreferences().size();
			requestedCourses[i] = new int[nbrPreferences];
			for (int j = 0; j < nbrPreferences; j++) {
				InputStudentCoursePreference preference = inputStudent.getCoursePreferences().get(j);
				int courseIndex = searchableCourses.indexOf(preference.getCourse().getName());
				requestedCourses[i][j] = courseIndex;
			}
			i++;
		}
		return requestedCourses;

	}

	private int[] buildCoursesOffered(String[] courses) {
		// all courses passed in are available to be assigned
		int[] offeredCourses = new int[courses.length];
		for (int i = 0; i < courses.length; i++) {
			offeredCourses[i] = i;
		}
		return offeredCourses;
	}

	private int[] buildCourseMaxEnrollment(OptimizerCalculation calculation) {
		int[] maxEnrollment = new int[calculation.getInputOfferedCourses().size()];
		int i = 0;
		for (InputOfferedCourse course : calculation.getInputOfferedCourses()) {
			maxEnrollment[i] = course.getMaxCourseSize() == -1 ? DEFAULT_MAX_COURSE_SIZE : course.getMaxCourseSize();
			i++;
		}
		return maxEnrollment;
	}

	public void calculateSchedule(OptimizerCalculation calculation) {

		String[] courses = populateCoursesAndLookup(calculation);
		String[] tas = populateTasAndLookup(calculation);
		String[] instructors = populateProfessorsAndLookup(calculation);
		int[][] instructorCompetencies = buildInstructorCompetencies(calculation, courses);
		String[] students = populateStudentsAndLookup(calculation);
		// 1. define some static data types... need to replace/wire into input
		// from Andrew's code
		// ***Need to replace 1 above with code to wire in from Andrew's code
		// 2. set up our Gurobi environment,
		// 3. define our constraints using the static data and contents loaded
		// from the file
		// 4. establish our objective function (to maximize total
		// StudentPreferenceMet)
		// 5. tell Gurobi to optimize the model
		// 6. print out our results (model dump)
		// ***Need to replace 6 above with code to return results
		//

		int maxStudents = 1000; // Upper bound to total Students; useful only to
								// get going; should come back and add dynamic
								// data structure
		int numStudents = students.length;
		int numInstructors = instructors.length;
		int maxCoursesPerInstructor = 1;
		int numTas = tas.length;
		int maxCoursesPerTa = 1;
		int numCourses = courses.length; // The catalog of courses are
											// statically defined in the
											// "courses" array.
		int maxStudentsPerTa = 50; // Per requirement ASM2 in requirements
									// tables
									// tables
		int defaultMaxRequestedCourses = 4; // Assumption for this solution is
											// that there are 12 classes
											// requested
		// declared this as separate variable for clarity when building student
		// schedule request constraints.

		int requestedCourses[][] = buildRequestedCourses(calculation, courses);
		// Statically defined max courses for each student for this semester (5
		// students per line)
		int maxRequestedCourses[] = buildMaximumDesiredCourseLoad(calculation);

		// statically defined credit hours completed to date for each student (5
		// students per line)
		int creditsCompleted[] = buildCreditsCompleted(calculation);

		// Semester Setup Info

		// statically defined courses offered (index into course array)
		int coursesOffered[] = buildCoursesOffered(courses);

		// assume all courseMaxEnrollments come in populated with value assigned
		// or default
		int courseMaxEnrollment[] = buildCourseMaxEnrollment(calculation);
		// in
		// each
		// position
		// corresponds
		// to
		// course
		// number
		// found
		// in
		// same
		// position
		// in
		// coursesOffered
		// array

		// statically defined instructor assignments.
		// first value is position in instructors; second value is course number
		// array index value

		int instructorAssignments[][] = buildInstructorCourseAssignments(calculation, courses, instructors);
		int numInstructorAssignments = instructorAssignments.length;
		int numCoursesOffered = coursesOffered.length;

		// Code below uses Gurobi

		GRBEnv env;
		try {

			// Setup Gurobi environment

			env = new GRBEnv("mip4.log");
			GRBModel model = new GRBModel(env);

			// Create gurobi variables

			// Student Course Enrollments
			GRBVar[][] yij = new GRBVar[numStudents][numCourses];

			for (int i = 0; i < numStudents; i++) {
				for (int j = 0; j < numCourses; j++) {
					String st = "Student_" + String.valueOf(i) + "_Course_" + String.valueOf(j);
					yij[i][j] = model.addVar(0, 1, 0.0, GRB.BINARY, st);
				}
			}

			// Instructor Course Assignments
			GRBVar[][] paj = new GRBVar[numInstructors][numCourses];

			for (int a = 0; a < numInstructors; a++) {
				for (int j = 0; j < numCourses; j++) {
					String st = "Instructor_" + String.valueOf(a) + "_Course_" + String.valueOf(j);
					paj[a][j] = model.addVar(0, 1, 0.0, GRB.BINARY, st);
				}
			}

			// TA Course Assignments
			GRBVar[][] taj = new GRBVar[numTas][numCourses];

			for (int a = 0; a < numTas; a++) {
				for (int j = 0; j < numCourses; j++) {
					String st = "TA_" + String.valueOf(a) + "_Course_" + String.valueOf(j);
					taj[a][j] = model.addVar(0, 1, 0.0, GRB.BINARY, st);
				}
			}

			GRBVar X = model.addVar(0, GRB.INFINITY, 1, GRB.INTEGER, "X");

			// Integrate new variables

			model.update();

			// Establish constraints

			// Implement Student Schedule Request Constraints -
			// A class must be in the student's requested classes (implemented
			// as classes not
			// on request list must not be assigned).
			for (int i = 0; i < numStudents; i++) { // for each student
				System.out.printf("\n i = %d\n", i);
				for (int r = 0; r < numCourses; r++) {

					if (!contains(requestedCourses[i], r)) {
						GRBLinExpr requestedCourseConstraint = new GRBLinExpr(); // new
																					// Linear
																					// Expression
																					// to
																					// hold
																					// classes
																					// requested
						requestedCourseConstraint.addTerm(1, yij[i][r]);
						String constraintName = "Requesting_Student_" + String.valueOf(i) + "_CourseNotRequested_"
								+ String.valueOf(r);
						model.addConstr(requestedCourseConstraint, GRB.EQUAL, 0, constraintName);
					}

				}
			}

			// Implement maxCoursesConstraint -
			// No student can take more than their max number of requested
			// classes per semester
			for (int i = 0; i < numStudents; i++) { // for each student
				GRBLinExpr maxCoursesConstraint = new GRBLinExpr(); // new
																	// Linear
																	// Expression
																	// to hold
																	// total of
																	// all
																	// classes
				for (int j = 0; j < requestedCourses[i].length; j++) { // ..and
																		// now
																		// for
																		// each
																		// class
					maxCoursesConstraint.addTerm(1, yij[i][j]);
				}
				String constraintName = "MAXCOURSE_Student_" + String.valueOf(i);
				model.addConstr(maxCoursesConstraint, GRB.LESS_EQUAL, maxRequestedCourses[i], constraintName);
			}

			// Implement maxCourseEnrollment Constraint
			// Can't have more students in a class than allowed by an
			// administrator
			for (int z = 0; z < numCoursesOffered; z++) { // for each course
															// that's offered
															// this term
				GRBLinExpr maxCourseEnrollmentConstraint = new GRBLinExpr(); // new
																				// linear
																				// expression
																				// to
																				// hold
																				// enrollment
																				// totals
																				// for
																				// classes
				for (int i = 0; i < numStudents; i++) {
					maxCourseEnrollmentConstraint.addTerm(1, yij[i][coursesOffered[z]]);
				}
				String constraintName = "MAXENROLLMENT_For_Course_"
						+ String.valueOf(coursesOffered[z] + "_is_" + String.valueOf(courseMaxEnrollment[z]));
				model.addConstr(maxCourseEnrollmentConstraint, GRB.LESS_EQUAL, courseMaxEnrollment[z], constraintName);

			}

			// Implement instructor competency constraint
			// Instructors can only teach courses they are competent in
			for (int a = 0; a < numInstructors; a++) { // for each instructor
				for (int j = 0; j < numCourses; j++) { // for each class
					if (!contains(instructorCompetencies[a], j)) {

						GRBLinExpr instructorCantTeach = new GRBLinExpr(); // new
																			// Linear
																			// Expression
																			// to
																			// hold
																			// total
																			// of
																			// students
																			// per
																			// class
						instructorCantTeach.addTerm(1, paj[a][j]);
						String constraintName = "Instructor_" + String.valueOf(a) + "_CantTeachCourse_"
								+ String.valueOf(j);
						System.out.printf(constraintName + "\n");
						model.addConstr(instructorCantTeach, GRB.EQUAL, 0, constraintName);
					}
				}
			}

			// Implement courses on offer must have one and only one instructor
			// constraint
			for (int z = 0; z < numCoursesOffered; z++) {
				GRBLinExpr instructorAssignment = new GRBLinExpr();
				for (int a = 0; a < numInstructors; a++) {
					instructorAssignment.addTerm(1, paj[a][coursesOffered[z]]);
				}
				String constraintName = "MUSTHAVEINSTRUCTOR_Course_" + String.valueOf(coursesOffered[z]);
				System.out.printf(constraintName + "\n");
				model.addConstr(instructorAssignment, GRB.EQUAL, 1, constraintName);
			}

			// Implement instructor can only teach maxCoursesPerInstructor
			// courses
			for (int a = 0; a < numInstructors; a++) {
				GRBLinExpr maxInstructorAssignment = new GRBLinExpr();
				for (int z = 0; z < numCourses; z++) {
					maxInstructorAssignment.addTerm(1, paj[a][z]);
				}
				String constraintName = "MAXCOURSES_of_" + String.valueOf(maxCoursesPerInstructor) + "_For_Instructor_"
						+ String.valueOf(a);
				System.out.printf(constraintName + "\n");
				model.addConstr(maxInstructorAssignment, GRB.LESS_EQUAL, maxCoursesPerInstructor, constraintName);
			}

			// Implement administrator instructor course assignment constraint
			for (int z = 0; z < numInstructorAssignments; z++) {
				GRBLinExpr adminInstructorAssignmentConstraint = new GRBLinExpr();
				adminInstructorAssignmentConstraint.addTerm(1,
						paj[instructorAssignments[z][0]][instructorAssignments[z][1]]);
				String constraintName = "INSTRUCTOR_" + String.valueOf(instructorAssignments[z][0]) + "_MUST_TEACH_"
						+ String.valueOf(instructorAssignments[z][1]);
				System.out.printf(constraintName + "\n");
				model.addConstr(adminInstructorAssignmentConstraint, GRB.EQUAL, 1, constraintName);
			}

			// Implement TA can only be assigned to maxCoursesPerTa courses
			for (int a = 0; a < numTas; a++) {
				GRBLinExpr maxTaAssignment = new GRBLinExpr();
				for (int z = 0; z < numCourses; z++) {
					maxTaAssignment.addTerm(1, taj[a][z]);
				}
				String constraintName = "MAXCOURSES_of_" + String.valueOf(maxCoursesPerTa) + "_For_Ta_"
						+ String.valueOf(a);
				System.out.printf(constraintName + "\n");
				model.addConstr(maxTaAssignment, GRB.LESS_EQUAL, maxCoursesPerTa, constraintName);
			}

			// Implement Necessary Number of TAs constraint (enforce
			// maxStudentsPerTA:1 ratio)
			for (int z = 0; z < numCoursesOffered; z++) { // for each course
															// that's offered
															// this term

				GRBLinExpr minTaAssignmentEnrollmentConstraint = new GRBLinExpr(); // new
																					// linear
																					// expression
																					// to
																					// hold
																					// enrollment
																					// totals
																					// for
																					// classes
				GRBLinExpr minTaAssignmentEnrollmentSupportedConstraint = new GRBLinExpr(); // new
																							// linear
																							// expression
																							// to
																							// hold
																							// enrollment
																							// supported
																							// by
																							// TA
																							// assignemnts

				for (int i = 0; i < numStudents; i++) {
					minTaAssignmentEnrollmentConstraint.addTerm(1, yij[i][coursesOffered[z]]);
				}

				for (int a = 0; a < numTas; a++) {
					minTaAssignmentEnrollmentSupportedConstraint.addTerm(maxStudentsPerTa, taj[a][coursesOffered[z]]);
				}
				String constraintName = "SUPPORTED_TA_RATIO_For_Course_" + String.valueOf(coursesOffered[z]);
				model.addConstr(minTaAssignmentEnrollmentConstraint, GRB.LESS_EQUAL,
						minTaAssignmentEnrollmentSupportedConstraint, constraintName);

			}
			/*
			 * 
			 * //Implement Necessary Number of TAs constraint (not more than 1
			 * above the maxStudentsPerTA:1 ratio) for (int z = 0; z <
			 * numCoursesOffered; z++) { //for each course that's offered this
			 * term
			 * 
			 * GRBLinExpr minTaAssignmentEnrollmentConstraint2 = new
			 * GRBLinExpr(); //new linear expression to hold enrollment totals
			 * for classes GRBLinExpr
			 * minTaAssignmentEnrollmentSupportedConstraint2 = new GRBLinExpr();
			 * //new linear expression to hold enrollment supported by TA
			 * assignemnts
			 * 
			 * for (int i = 0; i < numStudents; i ++ ) {
			 * minTaAssignmentEnrollmentConstraint2.addTerm(1,yij[i][
			 * coursesOffered[z]]); }
			 * 
			 * for (int a = 0; a < numTas; a ++) {
			 * minTaAssignmentEnrollmentSupportedConstraint2.addTerm(
			 * maxStudentsPerTa-1, taj[a][coursesOffered[z]]); } String
			 * constraintName =
			 * "SUPPORTED_TA_RATIO_For_Course2_"+String.valueOf(coursesOffered[z
			 * ]); model.addConstr(minTaAssignmentEnrollmentConstraint2,
			 * GRB.GREATER_EQUAL, minTaAssignmentEnrollmentSupportedConstraint2,
			 * constraintName);
			 * 
			 * }
			 */
			// Establish objective function

			// Implement Student Preferences Met - This is our objective...
			//
			GRBLinExpr studentPreferencesMet = new GRBLinExpr(); // new Linear
																	// Express
																	// to hold
																	// total
																	// student
																	// preferences
																	// met
			for (int i = 0; i < numStudents; i++) { // for each student
				for (int j = 0; j < requestedCourses[i].length; j++) { // ...for
																		// each
																		// class
					studentPreferencesMet.addTerm(creditsCompleted[i] / 3 + 1 / (j + 1), yij[i][j]);
					// coefficient in addTerm above is a function of academic
					// progress and where the class sits in a student's rank
					// order
				}
			}

			model.setObjective(studentPreferencesMet, GRB.MAXIMIZE);

			// Optimize the model
			model.optimize();
			/*
			 * GRBLinExpr minTaAssignments = new GRBLinExpr(); for (int z = 0; z
			 * < numCoursesOffered; z++) { for (int a = 0; a < numTas; a++) {
			 * minTaAssignments.addTerm(1, taj[a][coursesOffered[z]]); } }
			 * model.setObjective(minTaAssignments, GRB.MINIMIZE);
			 * 
			 * model.optimize();
			 */
			// Retrieve and print our results ...

			double ng_yij[][] = model.get(GRB.DoubleAttr.X, yij);
			double ng_paj[][] = model.get(GRB.DoubleAttr.X, paj);
			double ng_taj[][] = model.get(GRB.DoubleAttr.X, taj);

			// The solution is contained in the ng_vars declared above. However,
			// it will have to be mapped into
			// output object(s). To illustrate/show what's there/how to get to
			// it,
			// the code below dumps out the solution in text form.
			// To make the solution uniquely named, suggest we consider using
			// Julian date as primary key for results.
			if (model.get(GRB.IntAttr.Status) == GRB.Status.OPTIMAL) {
				double objectiveValue = model.get(GRB.DoubleAttr.ObjVal);
				System.out.printf("\nStudent Preferences Met (Ojective value) = %d\n", (int) objectiveValue);
				List<OutputUserCourseAssignment> outputStudents = new ArrayList<OutputUserCourseAssignment>();
				calculation.setOutputUserCourseAssignments(outputStudents);
				for (int j = 0; j < numCourses; j++) { // for every course...
					if (contains(coursesOffered, j)) { // if it's offered...
						System.out.printf("Course: [%d]" + courses[j] + "\n", j);// print
																					// the
																					// course
																					// name
						InputOfferedCourse inputCourse = courseNameToInputLookup.get(courses[j]);
						OutputOfferedCourse outputCourse = new OutputOfferedCourse();
						outputCourse.setCourse(inputCourse.getCourse());
						outputCourse.setOptimizerCalculation(calculation);
						calculation.getOutputOfferedCourses().add(outputCourse);

						for (int a = 0; a < numInstructors; a++) {// and for
																	// every
																	// instructor...
							if (ng_paj[a][j] == 1) {// if that instructor
													// teaches this course...

								outputCourse
										.setAssignedProfessor(professorNameToInputLookup.get(instructors[a]).getUser());
								System.out.printf("Instructor: " + instructors[a] + "\n");// then
																							// print
																							// out
																							// the
																							// instructor
																							// name
							}
						}
						List<OutputTACourseAssignment> courseTAs = new ArrayList<OutputTACourseAssignment>();
						for (int a = 0; a < numTas; a++) {// and for every TA...
							if (ng_taj[a][j] == 1) {// if he or she is assigned
													// to this course...
								OutputTACourseAssignment outputTA = new OutputTACourseAssignment();
								outputTA.setCourse(inputCourse.getCourse());
								outputTA.setOutputOfferedCourse(outputCourse);
								outputTA.setUser(tasNameToInputLookup.get(tas[a]).getUser());
								courseTAs.add(outputTA);
								System.out.printf("  TA - " + tas[a] + "\n");// then
																				// print
																				// out
																				// the
																				// TA's
																				// name
							}
						}
						outputCourse.setAssignedTAs(courseTAs);

						for (int i = 0; i < numStudents; i++) {// and for every
																// student...
							if (ng_yij[i][j] == 1) {// if he or she is assigned
													// to this course...
								OutputUserCourseAssignment outputStudent = new OutputUserCourseAssignment();
								outputStudent.setCourse(inputCourse.getCourse());
								outputStudent.setOptimizerCalculation(calculation);
								outputStudent.setUser(studentsNameToInputLookup.get(students[i]).getUser());
								outputStudents.add(outputStudent);
								System.out.printf("     [%d] " + students[i] + "\n", i);// ..then
																						// print
																						// out
																						// the
																						// student's
																						// name

							}
						}
					}
				}
			} else {
				System.out.printf("No Solution Possible.\n");
			}
			// Dispose of model and environment
			model.dispose();
			env.dispose();

		} catch (GRBException e) {
			e.printStackTrace();
		}

	}

}