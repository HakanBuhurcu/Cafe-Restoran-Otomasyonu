<?php

/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 10.08.2017
 * Time: 13:37
 */

  include_once "connection.php";
  header("content-type: text/html; charset=UTF-8");

  if( isset($_GET["groupNo"]) ){

      $controlTableQuery = $db -> prepare("SELECT * FROM CafeTable WHERE groupNo=:groupNo LIMIT 1");
      $controlTableQuery ->bindParam(":groupNo",$_GET["groupNo"]);
      if ($controlTableQuery -> execute()){
          if ($controlTableQuery -> rowCount() > 0){
              echo "alreadyDone";
              exit();
          }
      }

      if (isset($_GET["tableCount"]) and $_GET["tableCount"] != 0){
          $numberOfTtable = $_GET["tableCount"];

          for ( $i = 1; $i <= $numberOfTtable; $i++ ){
              $id = 0;
              $cafeTableQuery = $db -> prepare("INSERT INTO CafeTable VALUES (:tableID,:employeeID,:groupNo)");
              $cafeTableQuery -> bindParam(":tableID",$i);
              $cafeTableQuery -> bindParam(":employeeID",$id);
              $cafeTableQuery -> bindParam(":groupNo",$_GET["groupNo"]);
              if (!$cafeTableQuery -> execute())
                  break;
              if ($i == $numberOfTtable)
                  echo "done";
          }

      }else{
          echo "Table count undefined!";
      }






  }else{
      echo "no-post";
  }





