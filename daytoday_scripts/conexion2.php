<?php

    $hostname_localhost = "localhost";
    $database_localhost = "id19030363_database_daytoday";
    $username_localhost = "id19030363_databaseusername_daytoday";
    $password_localhost = "8CIf3OgRokBnoa]y";
    
    $mysql = new mysqli($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);
    
    if($mysql -> connect_error){
        die("Error " . $mysql -> connect_error);
    }

?>