<?php

/*
		1) Query for all Moods --> order by date
		2) Calculate averages for each day
		3) Format JSON
		4) Return JSON
*/

try 
{
  //create or open the database
  $db = new SQLiteDatabase('database', 0666, $error);
  
  $result = $db->prepare('SELECT * FROM data');
  $result->execute(array($inNodeID));
  $data = $result->fetchAll();

  foreach($data as $row){
  	
  }
	  
}
catch(Exception $e) 
{
  die($error);
}

?>
