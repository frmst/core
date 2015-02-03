-- phpMyAdmin SQL Dump
-- version 4.0.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:3306
-- Czas wygenerowania: 11 Wrz 2014, 11:44
-- Wersja serwera: 5.6.19-0ubuntu0.14.04.1
-- Wersja PHP: 5.5.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `heurix`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `security_roles_child_roles`
--

CREATE TABLE IF NOT EXISTS `security_roles_child_roles` (
  `role_id` bigint(20) NOT NULL,
  `child_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`child_role_id`),
  KEY `ca_child_role` (`child_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `security_roles_child_roles`
--

INSERT INTO `security_roles_child_roles` (`role_id`, `child_role_id`) VALUES
(1, 2);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `security_roles_child_roles`
--
ALTER TABLE `security_roles_child_roles`
  ADD CONSTRAINT `ca_child_role` FOREIGN KEY (`child_role_id`) REFERENCES `security_roles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ca_role` FOREIGN KEY (`role_id`) REFERENCES `security_roles` (`id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
