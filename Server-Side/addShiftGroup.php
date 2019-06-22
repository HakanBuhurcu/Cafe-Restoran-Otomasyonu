<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if(isset($_GET["groupNo"]) && isset($_GET["employeeID"])){
        $shiftGroupQuery = $db -> prepare("INSERT INTO ShiftGroup VALUES (:groupNo,:employeeID)");
        $shiftGroupQuery -> bindParam(":groupNo",$_GET["groupNo"]);
        $shiftGroupQuery -> bindParam(":employeeID",$_GET["employeeID"]);
        if ($shiftGroupQuery -> execute()){
            echo "done";
        }else {
            echo "Err;";
        }

}else{
     echo "no post";
}
