<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");


    if(isset($_GET["count"]) && isset($_GET["productID"]) && isset($_GET["tableID"]) && isset($_GET["BranchCode"])){
        
        $orderQuery = $db -> prepare("SELECT * FROM OrderTable Where tableID=:tableID AND productID=:productID LIMIT 1");
        $orderQuery -> bindParam(":tableID",$_GET["tableID"]);
        $orderQuery -> bindParam(":productID",$_GET["productID"]);
        $orderQuery -> execute();
        
        $count = $_GET["count"];
        
        /*
            Eğer bu masada daha önce bu productID sine sahip ürün sipariş edilmiş ise, ilk if işlemi çalışıyor.
            Yeni bir insert işlemi yapmak yerine var olan satırda update yapılara count miktarı değiştiriliyor.
            
            Eğer bu masada bu productID sine sahip ürün ilk defa sipariş edilmiş ise insert işlemi yapılarak count 1 e 
            sabitleniyor.
         */
        if($orderQuery -> rowCount() > 0){
            $result = $orderQuery -> fetch(PDO::FETCH_ASSOC);
            $count = $result["count"];
            $new = $count+$_GET["count"];
            
            $orderQuery = $db -> prepare("UPDATE OrderTable SET count=:count WHERE orderID=:orderID LIMIT 1");
            $orderQuery -> bindParam(":count",$new);
            $orderQuery -> bindParam(":orderID",$result["orderID"]);
            if(!$orderQuery -> execute())
                exit();
        }else{
            $nullVar = 1;
            $orderQuery = $db -> prepare("INSERT INTO OrderTable VALUES(null,:tableID,:productID,:count)");
            $orderQuery -> bindParam(":tableID",$_GET["tableID"]);
            $orderQuery -> bindParam(":productID",$_GET["productID"]);
            $orderQuery -> bindParam(":count",$count);
            if(!$orderQuery -> execute())
                exit();
        }
        $today = date("Y/m/d");
        $soldProductQuery = $db -> prepare("SELECT * FROM SoldProduct WHERE productID=:productID AND date=:date LIMIT 1");
        $soldProductQuery -> bindParam(":productID",$_GET["productID"]);
        $soldProductQuery -> bindParam(":date",$today);
        $soldProductQuery->execute();
        if($soldProductQuery -> rowCount() > 0 ){
            # Bu ürün için bugün yeni bir kayıt zaten açılmış.
            # Sadece count miktarında update yapılacak.
            $soldProductQuery = $db -> prepare("UPDATE SoldProduct SET count = (count + :count) WHERE productID=:productID AND date=:date LIMIT 1");
            $soldProductQuery -> bindParam(":count",$_GET["count"]);
            $soldProductQuery -> bindParam(":productID",$_GET["productID"]);
            $soldProductQuery -> bindParam(":date",$today);
            if ($soldProductQuery -> execute()){
                echo "done";
            }else{
                echo "Err;";
            }
            
        }else{
            # Bu ürün için bugün yeni kayıt açılacak.
            $soldProductQuery = $db -> prepare("INSERT INTO SoldProduct VALUES (null,:productID,:date,:count,:BranchCode)");
            $soldProductQuery -> bindParam(":count",$_GET["count"]);
            $soldProductQuery -> bindParam(":productID",$_GET["productID"]);
            $soldProductQuery -> bindParam(":date",$today);
			$soldProductQuery -> bindParam(":BranchCode",$_GET["BranchCode"]);
            if ($soldProductQuery -> execute()){
                echo "done";
            }else{
                echo "Error;";
            }

        }
        
        
}
