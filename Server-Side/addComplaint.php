<?php
include_once "connection.php";

if(isset($_GET["complaintText"])&& isset($_GET["branchCode"])){
	$date = date("Y/m/d H:i:s");
	$complaintQuery = $db -> prepare("INSERT INTO complaintbox VALUES(null,:complaint,:date,:branchCode)");
	$complaintQuery -> bindParam(":complaint",$_GET["complaintText"]);
	$complaintQuery -> bindParam(":date",$date);
	$complaintQuery -> bindParam(":branchCode",$_GET["branchCode"]);
	if($complaintQuery -> execute())
		echo "done";
	else
		echo "error";
}
else
	echo "no post";


