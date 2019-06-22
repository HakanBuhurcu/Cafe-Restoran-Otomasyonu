<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:56
 */

include_once "connection.php";
 
if( isset($_FILES["image"]["name"]) && isset($_POST["productID"]) ){
    
    $productID = $_POST["productID"];

    if(move_uploaded_file($_FILES["image"]["tmp_name"],"images/".$productID.".png")){
        echo "Ürün resmi başarıyla güncellendi.";
    }else{
        echo "FAIL";
    }



}else{
     echo "no post";
}
