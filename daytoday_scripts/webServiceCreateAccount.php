<?php
    include "conexion.php";

    if(isset($_REQUEST["nombre_user"]) && isset($_REQUEST["contrasena"]) && isset($_REQUEST["correo"])){
        $user = $_REQUEST["nombre_user"];
        $password = $_REQUEST["contrasena"];
        $correo = $_REQUEST["correo"];

        $apellidop = $_REQUEST["apellido_p"];
        $apellidom = $_REQUEST["apellido_m"];


        $consulta = "INSERT INTO usuarios VALUES (NULL, '$user', '$apellidop', '$apellidom', '$password', '$correo')";
        $resultado = mysqli_query($conexion, $consulta);

        mysqli_close($conexion);
        $json['usuario'][] = "Todo bien";
        echo json_encode($json); 
    }else{
        echo "ERROR";
    }

    ?>