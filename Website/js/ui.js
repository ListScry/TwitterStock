/*
	Authors : Jon Sandness, Stefan Mellem, Stephen Lee
	Date : 4-23-14
	What : This file exists to centralize all UI related javascript actions.
*/


// -- Chart UI Callbacks (Buttons clicked etc.)-- 

function calendarDateClicked(dateText){
	dateObj = new Date(dateText);
	updateDate(dateObj);
}

function dataClicked(e){
	date = e.dataPoint.x;
	dateStr = months[date.getMonth()] + " " + date.getDate() + ", " + date.getFullYear() ;
	weekdayStr = days[date.getDay()];
	openPriceStr = "" + ( Math.floor(Math.random()*11) * 2.324 + 1.23 )
	closePriceStr = "" + ( Math.floor(Math.random()*11) * 2.324 + 23.12 )
	volumeStr = "" + ( Math.floor(Math.random()*11) * 32 )

	$('#open-price').text(openPriceStr);
	$('#close-price').text(closePriceStr);
	$('#volume').text(volumeStr);
	$('#weekday').text(weekdayStr);
	$('#date').text(dateStr);

	fillTweets();
}


function updateDate(dateObj){
	dateStr = months[dateObj.getMonth()] + " " + dateObj.getDate() + ", " + dateObj.getFullYear() ;
	weekdayStr = days[dateObj.getDay()];
	openPriceStr = "" + ( Math.floor(Math.random()*11) * 2.324 + 1.23 )
	closePriceStr = "" + ( Math.floor(Math.random()*11) * 2.324 + 23.12 )
	volumeStr = "" + ( Math.floor(Math.random()*11) * 32 )

	$('#open-price').text(openPriceStr);
	$('#close-price').text(closePriceStr);
	$('#volume').text(volumeStr);
	$('#weekday').text(weekdayStr);
	$('#date').text(dateStr);

	fillTweets();
}


function parseTweet(){
	// NOTE: This will be passed in eventually
	tweetText = tweet1;
	
	moodValue = Math.floor(Math.random()*5 + 1);
	
	
	moodText = '<div class="tweet-mood-cont">\
					<div style="avgContainer"><center><h4>Mood is <i>' +
					moodValue +
					'</i></h4></center></div>	\
				</div>';
	
	
	tweetContainer = '<div class="tweet-text-cont">' + tweetText + '</div>'
	
	wholeContainer = '<div class="tweet-cont">' + moodText + tweetContainer + '</div>';	
	
				
	return wholeContainer;

}




function fillTweets(){
	//console.log([1,2,3].size())
	
	//allTweets = []

	//$('#tweetbucket1').text('')
	//$('#tweetbucket2').text('')

	// having a little fun
	for(var x = 0; x < 5; x ++){
		bucket1 = tweet1 + delim;
		bucket2 = tweet2 + delim;
		if( x % 2 == 0){
			bucket1 = tweet1 + delim;
			bucket2 = tweet2 + delim;
		}
		else{
			bucket2 = tweet1 + delim;
			bucket1 = tweet2 + delim;
		}
		
		bucket1 = parseTweet() + delim;
		bucket2 = parseTweet() + delim;
		
		$('#tweetbucket1').append(bucket1)
		$('#tweetbucket2').append(bucket2)
	}
}


window.onload = function () {
    var chart = new CanvasJS.Chart("chartContainer",
    {

      title:{	text: ""	},
      data: [
      {
        type: "line",
        click: function(e){	dataClicked(e);	},

        dataPoints: [
        			{ x: new Date(2014, 02, 1), y: 450 },
        			{ x: new Date(2014, 02, 2), y: 414 },
        			{ x: new Date(2014, 02, 3), y: 520 },
        			{ x: new Date(2014, 02, 4), y: 460 },
        			{ x: new Date(2014, 02, 5), y: 450 },
        			{ x: new Date(2014, 02, 6), y: 500 },
        			{ x: new Date(2014, 02, 7), y: 480 },
        			{ x: new Date(2014, 02, 8), y: 480 },
        			{ x: new Date(2014, 02, 9), y: 410 },
        			{ x: new Date(2014, 02, 10), y: 500 },
        			{ x: new Date(2014, 02, 11), y: 480 },
        			{ x: new Date(2014, 02, 12), y: 510 }	]}],
    	axisX:{
		  title: "Day",
		 },
		 axisY:{
		  title: "Price",
		 },
    });

    chart.render();
}