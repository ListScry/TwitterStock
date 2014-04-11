<?php

/*
		1) Query for tweets & Stock Data on a specific day
		2) Format data in JSON object
		3) Return data
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
