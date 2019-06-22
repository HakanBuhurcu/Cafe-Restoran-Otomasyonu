<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_GET["sale"]) &&  isset($_GET["productID"]) ){
    $productQuery = $db -> prepare("UPDATE product SET salePrice = :sale WHERE productID=:productID LIMIT 1");
    $productQuery -> bindParam(":sale",$_GET["sale"]);
    $productQuery -> bindParam(":productID",$_GET["productID"]);
    $productQuery -> execute();
    echo "done";
}else{
    echo "no post";
}
