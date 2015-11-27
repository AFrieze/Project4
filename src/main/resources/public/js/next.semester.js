(function($){
$(function(){
	var $available = $('#available-courses');
	var $selected =  $('#selected-courses');
	
	$available.on('click', 'a.move-to-selected', {} , function(e){
		e.preventDefault();
		$selected.append($(e.currentTarget).parent('.course').detach());
		$selected.trigger('gtproject4.coursechance');
	});
	
	$selected.on('click', 'a.move-to-unselected', {} , function(e){
		e.preventDefault();
		$available.append($(e.currentTarget).parent('.course').detach());
		$selected.trigger('gtproject4.coursechance');
	});
	
	$selected.on('click', 'a.move-to-unselected', {} , function(e){
		e.preventDefault();
		$available.append($(e.currentTarget).parent('.course').detach());
		$selected.trigger('gtproject4.coursechance');
	});
	
	$selected.on('click', 'a.move-up', {} , function(e){
		e.preventDefault();
		var $course = $(e.currentTarget).parent('.course');
		$course.prev().before($course.detach());
		$selected.trigger('gtproject4.coursechance');
	});
	
	$selected.on('click', 'a.move-down', {} , function(e){
		e.preventDefault();
		var $course = $(e.currentTarget).parent('.course');
		$course.next().after($course.detach());
		$selected.trigger('gtproject4.coursechance');
	});
	
	$selected.on('gtproject4.coursechance' , function(e){
		e.preventDefault();
		var numberOfCredits = 0;
		$selected.find('.course').each(function(){
			numberOfCredits += parseInt($(this).data('credits'));
		});
		$('#courseTotal').text(numberOfCredits);
	});
	
	$selected.trigger('gtproject4.coursechance');
	
	$('#submit').click(function(e){
		e.preventDefault();
		var selectedCourses = [];
		$selected.find('.course').each(function(){
			selectedCourses.push($(this).data('id'));
		});
		$('#selectedCourses').val(selectedCourses.join(','));
		$('#selectedCoursesFrom').submit();
		
	});

});
})(jQuery);