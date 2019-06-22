<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

    if(isset($_GET["orderID"])){

        $selectOrder = $db -> prepare("SELECT * FROM OrderTable WHERE orderID=:orderID LIMIT 1");
        $selectOrder -> bindParam(":orderID",$_GET["orderID"]);
        $count = null;
        $productID = null;
        $today = date("Y/m/d");

        if($selectOrder -> execute()){
            $res = $selectOrder -> fetch(PDO::FETCH_ASSOC);
            $count = $res["count"];
            $productID = $res["productID"];
        }
        
        if($count == null || $productID == null){
            echo "count ya da productID  boş";
            echo "count=".$count."\n";
            echo "productID=".$productID."\n";
            exit();
        }

        $orderQuery = $db -> prepare("DELETE FROM OrderTable Where orderID=:orderID");
        $orderQuery -> bindParam(":orderID",$_GET["orderID"]);
        
        $soldProductQuery = $db -> prepare("UPDATE SoldProduct SET count=(count - :count) WHERE productID=:productID AND date=:date LIMIT 1");
        $soldProductQuery->bindParam(":count",$count);
        $soldProductQuery->bindParam(":productID",$productID);
        $soldProductQuery->bindParam(":date",$today);
        
        if($orderQuery -> execute() && $soldProductQuery -> execute()){
            echo "done";
        }else{
            echo "Err;";
        }
        
        
    }else {
        
    }
    
