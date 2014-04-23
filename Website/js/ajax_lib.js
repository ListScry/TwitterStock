/*
	Authors : Jon Sandness, Stefan Mellem, Stephen Lee
	Date : 	4-23-14
	What : 	This is where all ajax requests go.
		 	Note - Format as ajaxFunction() and accompanying ajaxFunciton_Callback()
*/

/*
	Example AJAX call
	data = JSON returned from "getDataForDays.php"
*/
function exampleAJAXCall(){
	$.get( "ajax/getDataForDay.php", function( data ) {
		exampleAJAXCall_Callback(data)
	});
}

function exampleAJAXCall_Callback(data){
	json = data;
	
	alert("Works!");
}