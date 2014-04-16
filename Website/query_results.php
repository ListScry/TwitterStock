<?php

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

// Average mood values --> sent at first load
// binning and retrieving
// 3 bins --> before, during, after (market hours)
//			  before & after of adjacent days are the same

// Good way of charting both twitter and stock data
//			-- 2 charts for now

?>
