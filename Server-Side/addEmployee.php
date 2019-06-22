<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_GET["employeeTcNo"]) && isset($_GET["employeeName"]) && isset($_GET["employeePhone"]) && isset($_GET["employeeAddress"]) && isset($_GET["statuID"]) ){

    $productQuery = $db -> prepare("INSERT INTO Employee VALUES (null,:employeeTcNo,:employeeName,:employeePhone,:employeeAddress,:statuID)");
    $productQuery -> bindParam(":employeeTcNo", $_GET["employeeTcNo"]);
    $productQuery -> bindParam(":employeeName", $_GET["employeeName"]);
    $productQuery -> bindParam(":employeePhone",$_GET["employeePhone"]);
    $productQuery -> bindParam(":employeeAddress",$_GET["employeeAddress"]);
    $productQuery -> bindParam(":statuID",$_GET["statuID"]);
    if ($productQuery -> execute())
        echo "done";
    else
        echo "Err;";

}else{
     echo "no post";
}
