<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

if (isset($_GET["groupNo"])){
    $arr = array();

    $tableQuery = $db -> query("SELECT * FROM CafeTable WHERE employeeID=0 and groupNo=".$_GET["groupNo"],PDO::FETCH_ASSOC);

    foreach ($tableQuery as $tableResult){
        $arrTemp = array(
            "tableID"=>$tableResult["tableID"]);
        array_push($arr,$arrTemp);
    }

    echo json_encode($arr,JSON_UNESCAPED_UNICODE);

}else{
    echo "no post";
}