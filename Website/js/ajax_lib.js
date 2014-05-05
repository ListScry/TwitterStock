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
function getAllStockPrices(symbol, startDate, endDate){
	$.get( "ajax/getAllStockPrices.php?symbol="+symbol+"&startDate="+startDate+"&endDate="+endDate, function( data ) {
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
function getAvgMood(symbol, startDate, endDate){
	$.get( "ajax/getAvgMood.php?symbol="+symbol+"&startDate="+startDate+"&endDate="+endDate, function( data ) {
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
function getTweetsForDateBin(symbol, dateBin){
	$.get( "ajax/getTweetsForDateBin.php?symbol="+symbol+"&dateBin="+dateBin, function( data ) {
		getTweetsForDateBin_Callback(data)
	});
}

function getTweetsForDateBin_Callback(data){
	var json = data;
	console.log(json)
}