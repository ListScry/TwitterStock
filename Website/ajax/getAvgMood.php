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

  // Sum the Mood values for each Date in the range
  // TODO: switch to categorizing by bin rather than full days
  $totals = array();
  $counts = array();
  foreach($results as $row){
    if (!array_key_exists($row["Date"],$totals)){
      $totals[$row["Date"]]=$row["Mood"];
      $counts[$row["Date"]]=1;
    }
    else {
      $totals[$row["Date"]]+=$row["Mood"];
      $counts[$row["Date"]]++;
    }
  }

  // Calculate the average Mood for each Date in the range
  $averages = array();
  foreach($totals as $date => $val){
    $averages[$date]=$val/$counts[$date];
  }

  // Format & output results
  $json=json_encode($averages);
  echo $json;
}
catch(Exception $e) 
{
  die($error);
}

?>
    