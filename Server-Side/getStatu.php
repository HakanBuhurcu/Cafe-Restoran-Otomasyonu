<?php
        
    include_once "connection.php";
    header("content-type: text/html; charset=UTF-8");
        
    $arr = array();
        
    $employeeQuery = $db -> query("SELECT * FROM Statu",PDO::FETCH_ASSOC);
    foreach ($employeeQuery as $employeeResult){
        $tempArr = array(
                    "statuID"=>$employeeResult["statuID"],
                    "statuName"=>$employeeResult["statuName"]
                        );
            array_push($arr,$tempArr);
    }
    echo json_encode($arr,JSON_UNESCAPED_UNICODE);
        


