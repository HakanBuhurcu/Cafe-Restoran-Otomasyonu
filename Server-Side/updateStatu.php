<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

if( isset($_GET["statuName"]) && isset($_GET["statuID"]) ){

    $arr = array();

    $statuQuery = $db -> prepare("UPDATE Statu SET statuName=:statuName WHERE statuID=:statuID LIMIT 1 ");
    $statuQuery -> bindParam(":statuName",$_GET["statuName"]);
    $statuQuery -> bindParam(":statuID",$_GET["statuID"]);
    if ( $statuQuery -> execute() ){
        echo "done";
    }else {
        echo "Err;";
    }

}else{
    echo "no post";
}
