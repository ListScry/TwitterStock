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


// -------------------------------------------------
//	All Stock Prices
//  Example Request URL: ??
// -------------------------------------------------
function getAllStockPrices(){
	$.get( "ajax/getAllStockPrices.php", function( data ) {
		getAllStockPrices_Callback(data)
	});
}

function getAllStockPrices_Callback(data){
	var json = data;
	console.log(json)
}


// -------------------------------------------------
//	Average Mood
//  Example Request URL: ??
// -------------------------------------------------
function getAvgMood(){
	$.get( "ajax/getAvgMood.php", function( data ) {
		getAvgMood_Callback(data)
	});
}

function getAvgMood_Callback(data){
	var json = data;
	console.log(json)
}


// -------------------------------------------------
//	Tweets From A Bin
//  Example Request URL: ??
// -------------------------------------------------
function getTweetsForDateBin(){
	$.get( "ajax/getTweetsForDateBin.php", function( data ) {
		getTweetsForDateBin_Callback(data)
	});
}

function getTweetsForDateBin_Callback(data){
	var json = data;
	console.log(json)
}