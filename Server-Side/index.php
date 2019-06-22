<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

if( isset($_GET["employeeTcNo"]) && isset($_GET["employeePasswd"]) ){
    $userQuery = $db -> prepare("select * from User where employeeTcNo=:employeeTcNo and employeePasswd=:employeePasswd LIMIT 1");
    $userQuery -> bindParam(":employeeTcNo",$_GET["employeeTcNo"]);
    $userQuery -> bindParam(":employeePasswd",$_GET["employeePasswd"]);
    $userQuery -> execute();
    $arr = array();
    if($userQuery -> rowCount()){
        $arr["login"] = "ok";
        echo json_encode($arr);
    }else{
        $arr["login"] = "error";
        $arr["code"] = "200";
        echo json_encode($arr);
    }
}
