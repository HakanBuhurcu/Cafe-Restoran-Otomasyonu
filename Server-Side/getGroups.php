<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

$arr = array();

$tableQuery = $db -> query("SELECT * FROM ShiftGroup GROUP BY groupNo",PDO::FETCH_ASSOC);
foreach ($tableQuery as $tableResult){
    $arrTemp = array(
        "groupNo"=>$tableResult["groupNo"]);
    array_push($arr,$arrTemp);
}

echo json_encode($arr,JSON_UNESCAPED_UNICODE);
