<?php
    ini_set('display_errors', '1');
	error_reporting(E_ALL);
?>



<?php

/*
		1) Query for all daily stock prices (not detailed - Open/Closed)
		2) Format into JSON
		3) Return JSON
*/

header("Content-type: text/json");

try 
{
  // Open the database
  $db = new PDO('sqlite:actualdata.sqlite');
  $sql = "SELECT Date,Open,Close FROM Quote";

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
