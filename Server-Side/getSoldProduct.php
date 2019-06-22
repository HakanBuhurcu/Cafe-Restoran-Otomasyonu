<?php
    
    include_once "connection.php";
    header("content-type: text/html; charset=UTF-8");
    
        $arr = array();
        if(isset($_GET["startDate"]) && isset($_GET["endDate"])) {
        $soldQuery = $db -> prepare("SELECT C.categoryName,P.productName,P.costPrice,P.salePrice,SUM(SP.count) AS count,SP.BranchCode 
                    FROM Product P, SoldProduct SP,category C WHERE P.productID = SP.productID and C.categoryID = P.categoryID and SP.date >= :startDate and SP.date <= :endDate GROUP BY SP.productID");
		$soldQuery -> bindParam(":startDate",$_GET["startDate"]);
        $soldQuery -> bindParam(":endDate",$_GET["endDate"]);
		$soldQuery -> execute();
		$soldQuery = $soldQuery -> fetchAll(PDO::FETCH_ASSOC);

        foreach ( $soldQuery as $result){
            $tempArr = array(
                "categoryName" => $result["categoryName"],
                "productName" => $result["productName"],
                "sale" => $result["salePrice"],
                "cost" => $result["costPrice"],
                "count" => $result["count"],
				"BranchCode" => $result["BranchCode"]
            );
            array_push($arr,$tempArr);
        }
        echo json_encode($arr,JSON_UNESCAPED_UNICODE);
		}