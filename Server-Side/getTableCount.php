<?php

include_once "connection.php";
header("content-type: text/html; charset=UTF-8");


$tableCountQuery = $db -> query("SELECT * FROM TableCount");
$resultSet = $tableCountQuery -> fetch(PDO::FETCH_ASSOC);
echo $resultSet["tableCount"];