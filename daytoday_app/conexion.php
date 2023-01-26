<?php
    $hostname_localhost = "localhost";
    $database_localhost = "id19030363_database_daytoday";
    $username_localhost = "id19030363_databaseusername_daytoday";
    $password_localhost = "8CIf3OgRokBnoa]y";

    $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost) 
                OR DIE("Error de coneccion al servidor");
    ?>