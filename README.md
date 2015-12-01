# Project4
1. Database setup
    1. Download and install MySQL
    2. Update the username/password for the database in the hibernate.cfg.xml located in src/main/resources and src/test/resources
    3. Create two empty schemas: project4, project4test
2. Project setup
    1. Ensure that you have a version of eclipse installed which supports java 1.8
    2. From eclipse, import the provided .project file
    3. Update Maven Sources
    4. Add an external reference to the gurobi.jar file located on your system
    5. Ensure that the following environment variables are configured appropriately
        1. GUROBI_HOME
        2. PATH
        3. LD_LIBRARY_PATH
        4. GRB_LICENSE_FILE
    5. Run the Installer class located in org.gatechprojects.project5.installation
        1. This class attempts to upgrade the project4 schema to reflect the models registers in the DatabaseConfiguration class
3. Running the Application
    1. Start the ControllerService class passing in the semester id value for which you are optimizing as an argument
    2. Start the Server class
    3. Connect to the web application by navigating to the following url: http://localhost:4567


