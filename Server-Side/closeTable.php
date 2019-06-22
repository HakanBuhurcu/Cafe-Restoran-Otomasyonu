<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

    if(isset($_GET["tableID"])){

        $orderQuery = $db -> prepare("DELETE FROM OrderTable Where tableID=:tableID");
        $orderQuery -> bindParam(":tableID",$_GET["tableID"]);
        
        
        if($orderQuery -> execute()){
            echo "done";
        }else{
            echo "Err;";
        }
        
        
    }else {
        
    }
    
