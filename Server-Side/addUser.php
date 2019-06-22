<?php

/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 10.08.2017
 * Time: 13:37
 */

  include_once "connection.php";
  header("content-type: text/html; charset=UTF-8");

  if( isset($_GET["employeePasswd"]) &&  isset($_GET["employeeTcNo"])){
      $userQuery = $db -> prepare("INSERT INTO User VALUES (:employeeTcNo,:employeePasswd)");
      $userQuery ->bindParam(":employeeTcNo",$_GET["employeeTcNo"]);
      $userQuery ->bindParam(":employeePasswd",$_GET["employeePasswd"]);

      if ($userQuery -> execute()){
          echo "done";
      }else{
          echo "Err;";
      }
  }





