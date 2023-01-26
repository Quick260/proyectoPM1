<?php
    include "conexion.php";

    $json = array();

    $id_obra = $_REQUEST['id'];


    $consulta = "DELETE FROM `archivos` WHERE `archivos`.`id_archivos` = $id_obra";

    $resultado = mysqli_query($conexion, $consulta);

    mysqli_close($conexion);
    $json['usuario'][] = "Todo bien";
    echo json_encode($json); 
?>