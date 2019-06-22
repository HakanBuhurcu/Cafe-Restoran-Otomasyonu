<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_POST["name"]) && isset($_POST["cost"]) && isset($_POST["sale"]) && isset($_FILES["image"]["name"]) &&
   isset($_POST["categoryID"]) ){

    $nullVar = null;
    $productQuery = $db -> prepare("INSERT INTO Product VALUES (:productID,:categoryID,:productName,:costPrice,:salePrice)");
    $productQuery -> bindParam(":productID",$nullVar);
    $productQuery -> bindParam(":categoryID", $_POST["categoryID"]);
    $productQuery -> bindParam(":productName",$_POST["name"]);
    $productQuery -> bindParam(":costPrice",$_POST["cost"]);
    $productQuery -> bindParam(":salePrice",$_POST["sale"]);
    
    $productID = null;
    if($productQuery -> execute()){
        $productID = $db -> lastInsertId();
    }

    if(move_uploaded_file($_FILES["image"]["tmp_name"],"images/".$productID.".png")){
        echo "Ürün resmi başarıyla yüklendi ve ürün veritabanına kaydedildi.";
    }else{
        echo "FAIL";
    }



}else{
     echo "no post";
}
