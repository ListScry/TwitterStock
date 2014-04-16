<?php 
    ini_set('display_errors', '1');
	error_reporting(E_ALL);
?>



<?php

/*
		1) Query for tweets & Stock Data on a specific day
		2) Format data in JSON object
		3) Return data
*/

header("Content-type: text/json");

try 
{
  // Open the database
  $db = new PDO('sqlite:actualdata.sqlite');
  $sql = "SELECT * FROM Tweets";

  // Perform the query
  $statement = $db->prepare($sql);
  $statement->execute();
  $results=$statement->fetchAll(PDO::FETCH_ASSOC);
  
  // Format & output results
  $json=json_encode($results); 
  echo $json; 
	  
}
catch(Exception $e) 
{
  die($e);
}





/*
NOTE: Alternative way of formatting.  I don't like it as much.

foreach ( as $row)
{
	$ID = $row['ID'];
	$USER = $row['User'];
	$FOLLOWERS = $row['Followers'];
	$RETWEETS = $row['Retweets'];
	$TIMESTAMP = $row['Timestamp'];
	$MOOD = $row['Mood'];
	$KEYWORD = $row['Keyword'];
	$BINFLAG = $row['BinFlag'];
	$TEXT = $row['Text'];
	
	  	#print  .' - '. $row['Text'] . '<br /><br/>';
	}*/

#$result = $db->query('SELECT * FROM Tweets');
#$result->execute(array($inNodeID));

#var_dump($json)

#echo "Hello World 4";

?>


