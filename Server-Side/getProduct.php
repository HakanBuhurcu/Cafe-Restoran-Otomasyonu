<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

$arr = array();

$productQuery = $db -> query("SELECT productID,categoryName, productName, costPrice, salePrice,C.categoryID FROM product P, category C Where P.categoryID = C.categoryID and C.categoryID = 10",PDO::FETCH_ASSOC);
foreach ($productQuery as $productResult){
    $arrTemp = array(
        "categoryName"=>$productResult["categoryName"],
        "name"=>$productResult["productName"], "costPrice"=>$productResult["costPrice"],"salePrice"=>$productResult["salePrice"],
		"categoryID" => $productResult["categoryID"],
		"productID" => $productResult["productID"]);
    array_push($arr,$arrTemp);
}

echo json_encode($arr,JSON_UNESCAPED_UNICODE);
