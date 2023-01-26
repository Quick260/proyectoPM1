<?php
    include "conexion.php";

    $json = array();

    $id_obra = $_REQUEST['id'];
    $nombreObra = $_REQUEST['nom'];
    $tipoArt = $_REQUEST['art'];
    $fecha = $_REQUEST['fecha'];
    $descripcion = $_REQUEST['desc'];


    $consulta = "UPDATE `archivos` SET `nombre_obra` = '$nombreObra',
        `tipo_arte` = '$tipoArt',
        `fecha_obra` = '$fecha',
        `descripcion` = '$descripcion'
        WHERE `archivos`.`id_archivos` = $id_obra";

    $resultado = mysqli_query($conexion, $consulta);

    mysqli_close($conexion);
    $json['usuario'][] = "Todo bien";
    echo json_encode($json); 
?>