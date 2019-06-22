<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if(isset($_GET["groupNo"]) && isset($_GET["shiftRange"]) && isset($_GET["shiftDay"])){

    if(isset($_GET["first"]))
        // Öncelikle eski vardiya verileri siliniyor.
        $truncateQuery = $db -> query("TRUNCATE Shift");

    $shiftQuery = $db -> prepare("INSERT INTO Shift VALUES (:shiftDay, :groupNo,:shiftRange)");
    $shiftQuery -> bindParam(":shiftDay",$_GET["shiftDay"]);
    $shiftQuery -> bindParam(":groupNo",$_GET["groupNo"]);
    $shiftQuery -> bindParam(":shiftRange",$_GET["shiftRange"]);


    if ($shiftQuery -> execute()){
            echo "done";
        }else {
            echo "Err;";
        }

}else{
     echo "no post";
}
