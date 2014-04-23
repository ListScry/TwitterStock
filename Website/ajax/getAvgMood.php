<?php

/*
		1) Query for all Moods --> order by date
		2) Calculate averages for each day
		3) Format JSON
		4) Return JSON
*/

// Parameters
$startDate = $_GET['startDate'];
$endDate = $_GET['endDate'];

try 
{
  // Open the database
  $db = new PDO('sqlite:actualdata.sqlite');
  $sql = "SELECT Mood,Date FROM Tweets WHERE (Date>='$startDate' AND Date<='$endDate')" ;

  // Perform the query
  $statement = $db->prepare($sql);
  $statement->execute();
  $results=$statement->fetchAll(PDO::FETCH_ASSOC);

  // Currently averages Mood from ALL days in range; switch to getting an
  // average for each day
  $total = 0;
  $count = 0;
  foreach($results as $row){
    $total = $total+$row["Mood"];
    $count++
  }

  // Format & output results
  $json=json_encode($total/$count);
  echo $json;
}
catch(Exception $e) 
{
  die($error);
}

?>
    