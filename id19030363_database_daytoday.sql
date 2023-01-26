-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 06-01-2023 a las 21:08:45
-- Versión del servidor: 10.5.16-MariaDB
-- Versión de PHP: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id19030363_database_daytoday`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `archivos`
--

CREATE TABLE `archivos` (
  `id_archivos` int(11) NOT NULL,
  `nombre_obra` varchar(30) NOT NULL,
  `tipo_arte` varchar(30) NOT NULL,
  `fecha_obra` date NOT NULL,
  `usuario_obra` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `archivos`
--

INSERT INTO `archivos` (`id_archivos`, `nombre_obra`, `tipo_arte`, `fecha_obra`, `usuario_obra`, `descripcion`, `path`) VALUES
(23, 'prueba 2', 'Pintura al oleo', '2022-02-10', 63, 'prueba 2', 'http://kratoskique26.000webhostapp.com/daytoday/upload/22.jpg'),
(24, 'prueba 3', 'Escultura', '2022-01-05', 63, 'prueba 3', 'http://kratoskique26.000webhostapp.com/daytoday/upload/23.jpg'),
(26, 'fecha ', 'Pintura al oleo', '2023-01-01', 63, 'mimido', 'http://kratoskique26.000webhostapp.com/daytoday/upload/25.jpg'),
(27, 'perro', 'Otro', '2023-01-04', 64, '', 'http://kratoskique26.000webhostapp.com/daytoday/upload/26.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_user` int(11) NOT NULL,
  `nombre_user` varchar(30) NOT NULL,
  `apellido_p` varchar(30) DEFAULT NULL,
  `apellido_m` varchar(30) DEFAULT NULL,
  `contrasena` varchar(60) NOT NULL,
  `correo` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_user`, `nombre_user`, `apellido_p`, `apellido_m`, `contrasena`, `correo`) VALUES
(63, 'Enrique ', 'Romero ', 'Castro ', '18300402', 'enriquerc260@gmail.com'),
(64, 'Santiago ', 'Romero ', 'Castro', '123456789', 'santiagoromero2910@gmailsjeje');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `archivos`
--
ALTER TABLE `archivos`
  ADD PRIMARY KEY (`id_archivos`),
  ADD KEY `usuario_obra` (`usuario_obra`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `archivos`
--
ALTER TABLE `archivos`
  MODIFY `id_archivos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `archivos`
--
ALTER TABLE `archivos`
  ADD CONSTRAINT `archivos_ibfk_1` FOREIGN KEY (`usuario_obra`) REFERENCES `usuarios` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
