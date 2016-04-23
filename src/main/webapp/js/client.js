$(document).ready(function(){
   $('#login').on('submit', function(e){
       e.preventDefault();
       $('#alert').slideDown(300);
   }) ;

    $('.close').on('click', function(){
        $('#alert').slideUp(300);
    });
	
	 $('#calendar').fullCalendar({
        // put your options and callbacks here
    });
	
	$(function () {
    $('#datetimepicker1').datetimepicker();
  });
});