<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

    if(isset($_GET["tableID"])){
        $arr = array();

        $orderQuery = $db -> prepare("SELECT * FROM OrderTable Where tableID=:tableID");
        $orderQuery -> bindParam(":tableID",$_GET["tableID"]);
        $orderQuery -> execute();
        $orderQuery = $orderQuery -> fetchAll(PDO::FETCH_ASSOC);
        
        $counter = 1;
        foreach ($orderQuery as $orderResult){

            $productQuery = $db -> prepare("SELECT * FROM Product Where productID=:productID");
            $productQuery -> bindParam(":productID",$orderResult["productID"]);
            $productQuery -> execute();
            $productResult = $productQuery -> fetch(PDO::FETCH_ASSOC);

            $arrTemp = array(
                             "orderID"  => $orderResult["orderID"],
                             "productID"=>$orderResult["productID"],
                            "count"=>$orderResult["count"],
                            "name"=>$productResult["productName"],
                            "sale"=>$productResult["salePrice"]);
            array_push($arr,$arrTemp);
            $counter++;
        }
        
        echo json_encode($arr,JSON_UNESCAPED_UNICODE);

    }else {
        
    }
    
