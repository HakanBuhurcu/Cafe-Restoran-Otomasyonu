<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");

if (isset($_GET["tableCount"])){
    $tableCountQuery = $db -> prepare("UPDATE TableCount SET tableCount=:tableCount LIMIT 1");
    $tableCountQuery -> bindParam(":tableCount",$_GET["tableCount"]);
    if ($tableCountQuery -> execute()){
        echo "done";
    }
}

