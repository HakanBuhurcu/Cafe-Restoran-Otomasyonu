<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if(isset($_GET["statuName"])){

    $productQuery = $db -> prepare("INSERT INTO Statu VALUES (null,:statuName)");
    $productQuery -> bindParam(":statuName",$_GET["statuName"]);
    if ($productQuery -> execute())
        echo "done";
    else
        echo "Err;";

}else{
     echo "no post";
}
