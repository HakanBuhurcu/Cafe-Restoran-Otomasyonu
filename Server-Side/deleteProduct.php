<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_GET["productID"]) ){
    $productQuery = $db -> prepare("DELETE FROM product WHERE productID = :productID");
    $productQuery -> bindParam(":productID",$_GET["productID"]);
    $productQuery -> execute();
    echo "done";
}else{
    echo "no post";
}
