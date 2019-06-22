<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if(isset($_GET["categoryName"])){

    $productQuery = $db -> prepare("INSERT INTO Category VALUES (null,:categoryName)");
    $productQuery -> bindParam(":categoryName",$_GET["categoryName"]);
    if ($productQuery -> execute())
        echo "done";
    else
        echo "Err;";

}else{
     echo "no post";
}
