<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

if( isset($_GET["employeeID"])  ){
    $arr = array();
   $cafeTableQuery = $db -> prepare("SELECT * FROM CafeTable WHERE employeeID=:employeeID");
   $cafeTableQuery -> bindParam(":employeeID",$_GET["employeeID"]);
   $cafeTableQuery -> execute();
   foreach ($cafeTableQuery->fetchAll(PDO::FETCH_ASSOC) as $result){
       array_push($arr,$result["tableID"]);
   }
   echo json_encode($arr,JSON_UNESCAPED_UNICODE);
}else{
    echo "no post";
}
