<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

if( isset($_GET["employeeID"]) && isset($_GET["tables"]) && isset($_GET["groupNo"]) ){
    $tables = explode(":",$_GET["tables"]);

    $cafeTablePrepareQuery = $db -> prepare("UPDATE CafeTable SET employeeID = 0 WHERE employeeID=:employeeID");
    $cafeTablePrepareQuery -> bindParam(":employeeID",$_GET["employeeID"]);
    $cafeTablePrepareQuery -> execute();

    foreach ($tables as $table){
        $cafeTableQuery = $db -> prepare("UPDATE CafeTable SET employeeID = :employeeID WHERE tableID=:tableID and groupNo=:groupNo");
        $cafeTableQuery -> bindParam(":employeeID",$_GET["employeeID"]);
        $cafeTableQuery -> bindParam(":tableID",$table);
        $cafeTableQuery -> bindParam(":groupNo",$_GET["groupNo"]);
        $cafeTableQuery -> execute();
        if (($table == end($tables))){
            echo "done";
        }
    }
}else{
    echo "no post";
}
