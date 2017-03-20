-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mar 20 Décembre 2016 à 12:26
-- Version du serveur :  5.5.53-0+deb8u1
-- Version de PHP :  5.6.27-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

-- --------------------------------------------------------
--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `nameAccount` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `mail` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `lastname` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `firstname` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`nameAccount`, `mail`, `password`, `lastname`, `firstname`, `role`) VALUES
('Benji', 'benji@gmail.com', 'benjamin', 'Hilmoine', 'Benjamin', 'user'),
('dylan', 'd.dylan62138@gmail.com', 'user', 'test', 'dydy', 'user'),
('Elisyo', 'floguilbert@gmail.com', 'admin', 'Guilbert', 'Florian', 'admin'),
('Mme.Abeille', 'reineBzz@miel.com', 'abeille', 'Duthoit', 'Lucille', 'user'),
('test4', 'test@gmail.com', '123456789', 'test', 'test', 'user'),
('TestRefresh', 'refresh@gmail.com', 'refresh', 'test', 'refresh', 'user');

-- --------------------------------------------------------
--
-- Structure de la table `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `user1` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user2` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `friend`
--

INSERT INTO `friend` (`user1`, `user2`) VALUES
('Elisyo', 'dylan'),
('dylan', 'Elisyo');

-- --------------------------------------------------------

--
-- Structure de la table `friendsrequest`
--

CREATE TABLE IF NOT EXISTS `friendsrequest` (
`id` int(11) NOT NULL,
  `destinateur` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `destinataire` varchar(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `friendsrequest`
--

INSERT INTO `friendsrequest` (`id`, `destinateur`, `destinataire`) VALUES
(1, 'Mme.Abeille', 'Elisyo');

-- --------------------------------------------------------

--
-- Structure de la table `group`
--

CREATE TABLE IF NOT EXISTS `group` (
`id` int(11) NOT NULL,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `nameUser` varchar(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `group`
--

INSERT INTO `group` (`id`, `name`, `nameUser`) VALUES
(2, 'Groupe2', 'Elisyo'),
(5, 'test26', 'dylan'),
(6, 'test', 'dylan'),
(10, 'Maitre d''armes', 'Elisyo'),
(11, 'group pour Benji', 'Elisyo'),
(13, 'fjgbvjhbg', 'Elisyo');

-- --------------------------------------------------------

--
-- Structure de la table `hobbiesUser`
--

CREATE TABLE IF NOT EXISTS `hobbiesUser` (
  `nameAccount` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `idHobby` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `hobbiesUser`
--

INSERT INTO `hobbiesUser` (`nameAccount`, `idHobby`) VALUES
('dylan', 1),
('test4', 3),
('dylan', 5),
('dylan', 6),
('Elisyo', 6),
('Elisyo', 8),
('test4', 8),
('dylan', 11);

-- --------------------------------------------------------

--
-- Structure de la table `hobby`
--

CREATE TABLE IF NOT EXISTS `hobby` (
`id` int(10) NOT NULL,
  `categorie` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nom` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `hobby`
--

INSERT INTO `hobby` (`id`, `categorie`, `nom`) VALUES
(1, 'Sport collectif', 'Football'),
(2, 'Sport collectif', 'Rugby'),
(3, 'Sport collectif', 'HandBall'),
(4, 'Sport collectif', 'Volley-Ball'),
(5, 'Sport collectif', 'Basket-Ball'),
(6, 'Sport de combat', 'Boxe anglaise'),
(8, 'Sport de combat', 'K1'),
(11, 'Sport individuel', 'Tir a l''arc'),
(12, 'Sport individuel', 'Tennis');

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
`id` int(10) NOT NULL,
  `idGroupe` int(11) NOT NULL,
  `destinataire` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `contenue` text COLLATE utf8_unicode_ci NOT NULL,
  `dateCreation` date NOT NULL,
  `delais` int(11) NOT NULL,
  `iswithaccuse` tinyint(1) NOT NULL,
  `isprioritaire` tinyint(1) NOT NULL,
  `iscrypte` tinyint(1) NOT NULL,
  `state` varchar(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `message`
--

INSERT INTO `message` (`id`, `idGroupe`, `destinataire`, `contenue`, `dateCreation`, `delais`, `iswithaccuse`, `isprioritaire`, `iscrypte`, `state`) VALUES
(4, 5, 'Mme.Abeille', 'Tu es là tête de Lion ?', '2016-12-15', 0, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Structure de la table `messageDelai`
--

CREATE TABLE IF NOT EXISTS `messageDelai` (
`idMessage` int(11) NOT NULL,
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  `destructionDate` date NOT NULL,
  `nameAccount` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `idGroupe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- --------------------------------------------------------

--
-- Structure de la table `useringroup`
--

CREATE TABLE IF NOT EXISTS `useringroup` (
  `nameAccount` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `idGroup` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `useringroup`
--

INSERT INTO `useringroup` (`nameAccount`, `idGroup`) VALUES
('dylan', 2),
('dylan', 5),
('Elisyo', 5),
('Mme.Abeille', 5),
('dylan', 6),
('Elisyo', 6),
('Elisyo', 10),
('Elisyo', 11),
('Elisyo', 13);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `friend`
--
ALTER TABLE `friend`
 ADD PRIMARY KEY (`user1`,`user2`), ADD KEY `fk_friend_user2` (`user2`);

--
-- Index pour la table `friendsrequest`
--
ALTER TABLE `friendsrequest`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `destinateur` (`destinateur`), ADD UNIQUE KEY `destinataire` (`destinataire`);

--
-- Index pour la table `group`
--
ALTER TABLE `group`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `name` (`name`), ADD KEY `fk_us_group` (`nameUser`);

--
-- Index pour la table `hobbiesUser`
--
ALTER TABLE `hobbiesUser`
 ADD PRIMARY KEY (`nameAccount`,`idHobby`), ADD KEY `fk_hobby` (`idHobby`);

--
-- Index pour la table `hobby`
--
ALTER TABLE `hobby`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `nom` (`nom`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_message_group` (`idGroupe`), ADD KEY `fk_message_user` (`destinataire`);

--
-- Index pour la table `messageDelai`
--
ALTER TABLE `messageDelai`
 ADD PRIMARY KEY (`idMessage`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`nameAccount`), ADD UNIQUE KEY `mail` (`mail`);

--
-- Index pour la table `useringroup`
--
ALTER TABLE `useringroup`
 ADD PRIMARY KEY (`nameAccount`,`idGroup`), ADD KEY `idGroup` (`idGroup`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `friendsrequest`
--
ALTER TABLE `friendsrequest`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `group`
--
ALTER TABLE `group`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `hobby`
--
ALTER TABLE `hobby`
MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `messageDelai`
--
ALTER TABLE `messageDelai`
MODIFY `idMessage` int(11) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `friend`
--
ALTER TABLE `friend`
ADD CONSTRAINT `fk_friend_user1` FOREIGN KEY (`user1`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_friend_user2` FOREIGN KEY (`user2`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `friendsrequest`
--
ALTER TABLE `friendsrequest`
ADD CONSTRAINT `friendsrequest_ibfk_1` FOREIGN KEY (`destinateur`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `friendsrequest_ibfk_2` FOREIGN KEY (`destinataire`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `group`
--
ALTER TABLE `group`
ADD CONSTRAINT `fk_us_group` FOREIGN KEY (`nameUser`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `hobbiesUser`
--
ALTER TABLE `hobbiesUser`
ADD CONSTRAINT `fk_hobby` FOREIGN KEY (`idHobby`) REFERENCES `hobby` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_user` FOREIGN KEY (`nameAccount`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
ADD CONSTRAINT `fk_message_group` FOREIGN KEY (`idGroupe`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_message_user` FOREIGN KEY (`destinataire`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `useringroup`
--
ALTER TABLE `useringroup`
ADD CONSTRAINT `useringroup_ibfk_1` FOREIGN KEY (`nameAccount`) REFERENCES `user` (`nameAccount`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `useringroup_ibfk_2` FOREIGN KEY (`idGroup`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
