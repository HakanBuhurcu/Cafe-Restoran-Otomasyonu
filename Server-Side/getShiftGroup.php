<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";

$esasArr = array();

$shiftGroupQuery = $db -> query("SELECT groupNo FROM ShiftGroup GROUP BY groupNo",PDO::FETCH_ASSOC);
foreach ($shiftGroupQuery as $result){

    $innerArr = array(); // Employee Info kısmını tutan array.

    $queryAgain = $db -> prepare("SELECT * FROM ShiftGroup SG, Employee E WHERE SG.employeeID = E.employeeID AND SG.groupNo=:groupNo");
    $queryAgain -> bindParam(":groupNo",$result["groupNo"]);
    $queryAgain -> execute();

    foreach ($queryAgain -> fetchAll(PDO::FETCH_ASSOC) as $resultAgain){
        array_push($innerArr,array("employeeID"=>$resultAgain["employeeID"],"employeeName"=>$resultAgain["employeeName"]));
    }
    $arr = array("groupNo"=>$result["groupNo"],"employeeInfo"=>$innerArr);

    array_push($esasArr,$arr);

}

echo json_encode($esasArr,JSON_UNESCAPED_UNICODE);