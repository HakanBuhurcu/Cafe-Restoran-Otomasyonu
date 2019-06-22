<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

if( isset($_GET["employeeID"]) ){

    $arr = array();

    $statuQuery = $db -> prepare("UPDATE Employee SET statuID=:statuID WHERE employeeID=:employeeID LIMIT 1 ");
    $statuID = 1; // eski çalışan statuID'si.
    $statuQuery -> bindParam(":statuID",$statuID);
    $statuQuery -> bindParam(":employeeID",$_GET["employeeID"]);

    if ( $statuQuery -> execute() ){
        echo "done";
    }else {
        echo "Err;";
    }

}else{
    echo "no post";
}
