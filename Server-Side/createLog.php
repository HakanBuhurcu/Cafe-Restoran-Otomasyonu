<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if(isset($_GET["logContent"])){

    $date = date("Y/m/d H:i:s");
   $logQuery = $db -> prepare("INSERT INTO Log VALUES (null,:logContent,:dateTime)");
   $logQuery -> bindParam(":logContent",$_GET["logContent"]);
   $logQuery -> bindParam(":dateTime",$date);
   $logQuery -> execute();

}else{
     echo "no post";
}
