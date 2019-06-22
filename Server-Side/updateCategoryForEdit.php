<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_GET["categoryID"]) && isset($_GET["categoryName"])){
    $categoryQuery = $db -> prepare("UPDATE Category SET categoryName = :categoryName WHERE categoryID=:categoryID LIMIT 1");
    $categoryQuery -> bindParam(":categoryName",$_GET["categoryName"]);
    $categoryQuery -> bindParam(":categoryID",$_GET["categoryID"]);
    $categoryQuery -> execute();
    echo "done";
}else{
    echo "no post";
}
