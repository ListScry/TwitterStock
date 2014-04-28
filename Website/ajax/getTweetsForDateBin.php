<?php

/*
		1) Query for all Moods --> order by date
		2) Calculate averages for each day
		3) Format JSON
		4) Return JSON
*/

// Parameters
$dateBin = $_GET['dateBin'];

try 
{
  // Open the database
  $db = new PDO('sqlite:actualdata.sqlite');
  $sql = "SELECT * FROM Tweets WHERE (DateBin=='$dateBin')" ;

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
  die($error);
}

?>
    