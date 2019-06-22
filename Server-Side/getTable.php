<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

$arr = array();

$tableQuery = $db -> query("SELECT * FROM CafeTable",PDO::FETCH_ASSOC);
    $counter = 1;
foreach ($tableQuery as $tableResult){
    $arrTemp = array(
        "tableID"=>$counter);
    array_push($arr,$arrTemp);
    $counter++;
}

echo json_encode($arr,JSON_UNESCAPED_UNICODE);
