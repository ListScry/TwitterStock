/*
	Authors : Jon Sandness, Stefan Mellem, Stephen Lee
	Date : 	4-23-14
	What : 	Main javascript file.  Things that don't make sense to be abstracted yet.
*/

// --- Variable Declarations ---

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
	
var json = {};

// --- SETUP For DatePicker ---
// Used for filter for range
var startDate = (new Date(2014, 4 - 1, 16))
var endDate = (new Date(2014, 4 - 1, 23))

// Initialize DatePicker
$( "#datepicker" ).datepicker({
			inline: true,
			minDate:startDate,
			maxDate:endDate,
			onSelect: function(dateText, inst) { calendarDateClicked(dateText); }
		});
        	
			
// --- UI Initialization ---
dataClicked(obj);
fillTweets();