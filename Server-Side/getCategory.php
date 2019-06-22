<?php

    include_once("connection.php");
    
    header("content-type: text/html; charset=UTF-8");  

    $arr = array();
    
    $categoryQuery = $db -> query("SELECT * FROM Category");
    $res = $categoryQuery -> fetchAll(PDO::FETCH_ASSOC);
    foreach($res as $result){
        array_push($arr,$result["categoryName"]);
    }
    echo json_encode($arr,JSON_UNESCAPED_UNICODE);
