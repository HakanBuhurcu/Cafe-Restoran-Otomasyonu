<?php
/**
 * Created by PhpStorm.
 * User: furkankaplan
 * Date: 16.07.2017
 * Time: 17:54
 */

$user = "root";
$pass = "";
$db = new PDO('mysql:host=localhost;dbname=PetronetOtomasyon', $user, $pass);
$db -> exec("SET NAMES utf8");

