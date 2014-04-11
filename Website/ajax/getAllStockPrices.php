<?php

/*
		1) Query for all daily stock prices (not detailed - Open/Closed)
		2) Format into JSON
		3) Return JSON
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
