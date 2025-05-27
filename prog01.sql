-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-05-2025 a las 23:32:12
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `prog01`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matriculados`
--

CREATE TABLE `matriculados` (
  `codiEstdWeb` int(11) NOT NULL,
  `ndniEstdWeb` varchar(50) DEFAULT NULL,
  `appaEstdWeb` varchar(50) DEFAULT NULL,
  `apmaEstdWeb` varchar(50) DEFAULT NULL,
  `nombEstdWeb` varchar(50) DEFAULT NULL,
  `fechNaciEstdWeb` date DEFAULT NULL,
  `logiEstd` varchar(100) DEFAULT NULL,
  `passEstd` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `matriculados`
--

INSERT INTO `matriculados` (`codiEstdWeb`, `ndniEstdWeb`, `appaEstdWeb`, `apmaEstdWeb`, `nombEstdWeb`, `fechNaciEstdWeb`, `logiEstd`, `passEstd`) VALUES
(1, '1234567', 'Chinga', 'Ramos', 'Carlos', '2025-05-07', 'kike', '1234'),
(2, '1234567', 'Andres', 'Mora', 'Pacheco', '2025-05-27', 'andres', '1234');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `matriculados`
--
ALTER TABLE `matriculados`
  ADD PRIMARY KEY (`codiEstdWeb`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `matriculados`
--
ALTER TABLE `matriculados`
  MODIFY `codiEstdWeb` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
