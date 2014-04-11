<body>



<div style="text-align:left;">
	<h1>Twitter / Stock Analysis</h1>
	<div id="chartContainer" style="height: 400px; width: 900px;"></div>


	<div style="width:500px;">
		<h2 style="border-bottom:solid;" id="date">March 1</h2>
		<h3><span id="weekday"></span></h3>
		<b>Opening Price : </b>$<span id="open-price"></span><br/>
		<b>Closing Price : </b>$<span id="close-price"></span><br/>
		<b>Volume : </b><span id="volume"></span><br/>
	</div>

	<div style="width:800;">
		<div style="width:400px;float:left;">
			<h3>Tweet Bucket 1</h3>
			<div id="tweetbucket1">
			</div>
		</div>
		<div style="width:400px;float:left;">
			<h3>Tweet Bucket 2</h3>
			<div id="tweetbucket2">
			</div>
		</div>
	</div>


</div>

<script async src="http://platform.twitter.com/widgets.js" charset="utf-8"></script>
<script type="text/javascript" src="/static/canvasjs.min.js"></script>
<script type="text/javascript" src="/static/jquery-2.1.0.min.js"></script> 

<script>

tweet1 = '<blockquote  lang="en"><p>The show&#39;s getting (Glenn) Close. I better put on my (Benedict) Cumberbatch and my (Jennifer) Garner belt. Okay, I&#39;m done. <a href="https://twitter.com/search?q=%23Oscars&amp;src=hash">#Oscars</a></p>&mdash; Ellen DeGeneres (@TheEllenShow) <a href="https://twitter.com/TheEllenShow/statuses/440216822558633984">March 2, 2014</a></blockquote>';

tweet2 = '<blockquote  lang="en"><p>It&#39;s a beautiful (Daniel) Day (Lewis)! Only 3 more until the Oscars. Are ya excited? Ewan (McGregor) me both. <a href="https://twitter.com/search?q=%23Oscars&amp;src=hash">#Oscars</a></p>&mdash; Ellen DeGeneres (@TheEllenShow) <a href="https://twitter.com/TheEllenShow/statuses/439204155282821120">February 28, 2014</a></blockquote>'

delim = '<div style="width:380px;height:2px;background-color:black;"></div>'


// Some variables to make text look nice
var months = new Array("January", "February", "March", 
"April", "May", "June", "July", "August", "September", 
"October", "November", "December");

var days = new Array("Monday", "Tuesday", "Tuesday", 
"Wednesday", "Thursday", "Friday", "Saturday", "Sunday"	);

// Temp data - to set things up on page load
var obj = {
		dataPoint: {
			x: new Date(2014, 02, 1)
		}
	};
        			
dataClicked(obj);
fillTweets();




/*
	Example AJAX call
	data = JSON returned from "getAllStockPrices.php"
*/
$.get( "ajax/getAllStockPrices.php", function( data ) {
  	$( "body" ).html( data );
  	alert( "Load was performed." );
});





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

function fillTweets(){
	allTweets = []

	$('#tweetbucket1').text('')
	$('#tweetbucket2').text('')

	// having a little fun
	for(var x = 0; x < 20; x ++){
		if( x % 2 == 0){
			$('#tweetbucket1').append(tweet1 + delim)
			$('#tweetbucket2').append(tweet2 + delim)
		}
		else{
			$('#tweetbucket1').append(tweet2 + delim)
			$('#tweetbucket2').append(tweet1 + delim)
		}
		

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

	
</script>

<?php include("query_results.php"); ?>

</body>