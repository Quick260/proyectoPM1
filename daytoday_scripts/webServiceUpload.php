<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("conexion2.php");

        $imageData = $_POST['path'];
        $idUsuario = intval($_POST['idUsuario']);
        $nombreObra = $_POST['nombreObra'];
        $tipo = $_POST['tipo'];
        $fecha = $_POST['fecha'];
        $desc = $_POST['desc'];

        $query = "SELECT id_archivos FROM archivos ORDER BY id_archivos ASC";
        $result = $mysql -> query($query);

        while($row = $result -> fetch_array()){
            $defaultId = $row['id_archivos'];
        }

        $imagePath = "upload/$defaultId.jpg";
        $SERVER_URL = "http://kratoskique26.000webhostapp.com/daytoday/$imagePath";

        $queryInsert = 
        "INSERT INTO archivos VALUES 
        (NULL, '$nombreObra', '$tipo', '$fecha', '$idUsuario', '$desc', '$SERVER_URL')";

        $resultInsert = $mysql -> query($queryInsert);

        if($resultInsert === TRUE){
            file_put_contents($imagePath, base64_decode($imageData));
        }
        $mysql -> close();
    }

?>