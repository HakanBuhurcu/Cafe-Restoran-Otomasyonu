<?php
        
    include_once "connection.php";
    header("content-type: text/html; charset=UTF-8");
        
    $arr = array();
        
    $employeeQuery = $db -> query("SELECT employeeID,employeeTcNo,employeeName,employeePhone,employeeAddress,statuName,S.statuID FROM Employee E,statu S Where E.statuID = S.statuID",PDO::FETCH_ASSOC);
    foreach ($employeeQuery as $employeeResult){
        $tempArr = array(
                    "employeeID"=>$employeeResult["employeeID"],
                    "employeeTcNo"=>$employeeResult["employeeTcNo"],
                    "employeeName"=>$employeeResult["employeeName"],
                    "employeePhone"=>$employeeResult["employeePhone"],
                    "employeeAddress"=> $employeeResult["employeeAddress"],
                    "statuName" => $employeeResult["statuName"],
					"statuID" => $employeeResult["statuID"]
                        );
            array_push($arr,$tempArr);
    }
    echo json_encode($arr,JSON_UNESCAPED_UNICODE);
        


