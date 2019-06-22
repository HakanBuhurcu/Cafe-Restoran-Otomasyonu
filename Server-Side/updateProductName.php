<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_GET["productName"]) &&  isset($_GET["productID"]) ){
    $productQuery = $db -> prepare("UPDATE product SET productName = :productName WHERE productID=:productID LIMIT 1");
    $productQuery -> bindParam(":productName",$_GET["productName"]);
    $productQuery -> bindParam(":productID",$_GET["productID"]);
    $productQuery -> execute();
    echo "done";
}else{
    echo "no post";
}
