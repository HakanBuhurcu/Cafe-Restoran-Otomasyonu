<?php
    
    include_once "connection.php";
    header("content-type: text/html; charset=UTF-8");
    
        $arr = array();
        
        $branchQuery = $db -> query("SELECT BranchCode, BranchName,Adress,Phone FROM branch ", PDO::FETCH_ASSOC);

        foreach ( $branchQuery as $result){
            $tempArr = array(
			    "BranchCode" => $result["BranchCode"],
                "BranchName" => $result["BranchName"],
				"BranchAdress" => $result["Adress"],
				"BranchPhone" => $result["Phone"]
            );
            array_push($arr,$tempArr);
        }
        echo json_encode($arr,JSON_UNESCAPED_UNICODE);
