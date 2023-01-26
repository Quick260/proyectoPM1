<?php
    include "conexion.php";

    $json = array();

    $id_usuario = $_REQUEST['id'];
    $consulta = "SELECT * FROM archivos WHERE usuario_obra = $id_usuario";
    $resultado = mysqli_query($conexion, $consulta);

    while($registro = mysqli_fetch_array($resultado)){
        $json['archivo'][] = $registro;
    }

    mysqli_close($conexion);
    echo json_encode($json);
?>