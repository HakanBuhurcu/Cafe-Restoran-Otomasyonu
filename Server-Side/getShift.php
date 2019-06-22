<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

$arr = array();
$shiftQuery = $db -> query("SELECT * FROM Shift",PDO::FETCH_ASSOC);

foreach ($shiftQuery as $result){

    $arrTemp = array(
        "shiftDay"  => $result["shiftDay"],
        "groupNo" => $result["groupNo"],
        "shiftRange" => $result["shiftRange"]);
    array_push($arr,$arrTemp);
}

echo json_encode($arr,JSON_UNESCAPED_UNICODE);

    
