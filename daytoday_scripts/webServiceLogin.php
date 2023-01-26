<?php
    include "conexion.php";
    $json = array();

    if(isset($_REQUEST["contrasena"]) && isset($_REQUEST["correo"])){

        $password = $_REQUEST["contrasena"];
        $correo = $_REQUEST["correo"];

        $consulta = "SELECT * FROM usuarios WHERE correo = '$correo' AND contrasena = '$password'";
        $resultado = mysqli_query($conexion, $consulta);

        $login = NULL;
            while ($row = $resultado->fetch_assoc()) {
                $login['id_user'] = $row['id_user'];
                $login['nombre_user'] = $row['nombre_user'];
                $login['apellido_p'] = $row['apellido_p'];
                $login['apellido_m'] = $row['apellido_m'];
                $login['password'] = $row['contrasena'];
                $login['correo'] = $row['correo'];
            }

            $json['usuario'][] = $login;

        mysqli_close($conexion);
        echo json_encode($json);
    }else{
        $login['id_user'] = 0;
        $login['nombre_user'] = "WS no retorna";
        $login['apellido_p'] = "WS no retorna";
        $login['apellido_m'] = "WS no retorna";
        $login['password'] = "WS no retorna";
        $login['correo'] = "WS no retorna";
        $json['usuario'][] = $login;
        mysqli_close($conexion);
        echo json_encode($json);
    }
?>