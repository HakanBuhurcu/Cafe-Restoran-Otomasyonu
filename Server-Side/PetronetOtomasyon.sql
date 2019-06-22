-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 22 Ağu 2017, 14:47:52
-- Sunucu sürümü: 10.1.21-MariaDB
-- PHP Sürümü: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `PetronetOtomasyon`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `CafeTable`
--

CREATE TABLE `CafeTable` (
  `tableID` int(11) NOT NULL,
  `employeeID` int(11) NOT NULL,
  `groupNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Category`
--

CREATE TABLE `Category` (
  `categoryID` int(11) NOT NULL,
  `categoryName` varchar(250) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `Category`
--

INSERT INTO `Category` (`categoryID`, `categoryName`) VALUES
(1, 'Hamburger'),
(2, 'Pizza'),
(4, 'Sulu Yemek'),
(5, 'Tatlı'),
(6, 'Sıcak İçeçek'),
(7, 'Alkollü İçecekler'),
(8, 'Sandviçler'),
(9, 'Tostlar');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Employee`
--

CREATE TABLE `Employee` (
  `employeeID` int(11) NOT NULL,
  `employeeTcNo` text COLLATE utf8_turkish_ci NOT NULL,
  `employeeName` text COLLATE utf8_turkish_ci NOT NULL,
  `employeePhone` text COLLATE utf8_turkish_ci NOT NULL,
  `employeeAddress` text COLLATE utf8_turkish_ci NOT NULL,
  `statuID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `Employee`
--

INSERT INTO `Employee` (`employeeID`, `employeeTcNo`, `employeeName`, `employeePhone`, `employeeAddress`, `statuID`) VALUES
(1, '0', 'admin', '000 000 00 00', 'admin', 2),
(2, '33322235126', 'Furkan KAPLAN', '0538 459 01 34', 'antalya', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Log`
--

CREATE TABLE `Log` (
  `logID` int(11) NOT NULL,
  `process` text COLLATE utf8_turkish_ci NOT NULL,
  `dateTime` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `Log`
--

INSERT INTO `Log` (`logID`, `process`, `dateTime`) VALUES
(1, '1 sisteme giriş yaptı', '2017/08/22 13:14:05'),
(2, '1 sistemden çıkış yaptı', '2017/08/22 13:17:26'),
(3, '1 sisteme giriş yaptı', '2017/08/22 13:29:07'),
(4, 'eski+%C3%A7al%C4%B1%C5%9Fan adlı statü sisteme eklendi', '2017/08/22 13:29:29'),
(5, 'y%C3%B6netici adlı statü sisteme eklendi', '2017/08/22 13:29:39'),
(6, 'admin sisteme giriş yaptı', '2017/08/22 13:50:56'),
(7, 'Furkan+KAPLAN adlı çalışan sisteme eklendi', '2017/08/22 13:57:30'),
(8, 'admin sisteme giriş yaptı', '2017/08/22 14:18:16'),
(9, '2 ID\'li çalışana sisteme giriş yetkisi verildi', '2017/08/22 14:18:48'),
(10, 'admin sistemden çıkış yaptı', '2017/08/22 14:18:51'),
(11, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:18:56'),
(12, 'Furkan KAPLAN sistemden çıkış yaptı', '2017/08/22 14:19:01'),
(13, 'admin sisteme giriş yaptı', '2017/08/22 14:19:05'),
(14, '2 ID\'li çalışan bilgileri güncellendi.', '2017/08/22 14:19:17'),
(15, 'admin sistemden çıkış yaptı', '2017/08/22 14:19:20'),
(16, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:19:25'),
(17, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:21:24'),
(18, 'admin sisteme giriş yaptı', '2017/08/22 14:23:50'),
(19, 'admin sisteme giriş yaptı', '2017/08/22 14:24:18'),
(20, 'admin sistemden çıkış yaptı', '2017/08/22 14:24:25'),
(21, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:24:31'),
(22, 'Furkan KAPLAN sistemden çıkış yaptı', '2017/08/22 14:25:09'),
(23, 'admin sisteme giriş yaptı', '2017/08/22 14:25:17'),
(24, 'İşletmedeki masa sayısı 10 olarak güncellendi', '2017/08/22 14:25:22'),
(25, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:32:06'),
(26, 'Furkan KAPLAN sisteme giriş yaptı', '2017/08/22 14:32:57'),
(27, 'admin sisteme giriş yaptı', '2017/08/22 14:33:30'),
(28, 'admin sisteme giriş yaptı', '2017/08/22 14:34:44'),
(29, '1 numaralı masa kapatıldı', '2017/08/22 14:35:57'),
(30, '1 numaralı masa kapatıldı', '2017/08/22 14:36:49');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `OrderTable`
--

CREATE TABLE `OrderTable` (
  `orderID` int(11) NOT NULL,
  `tableID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Product`
--

CREATE TABLE `Product` (
  `productID` int(11) NOT NULL,
  `categoryID` int(11) NOT NULL,
  `productName` varchar(150) COLLATE utf8_turkish_ci NOT NULL,
  `costPrice` varchar(11) COLLATE utf8_turkish_ci NOT NULL,
  `salePrice` varchar(11) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `Product`
--

INSERT INTO `Product` (`productID`, `categoryID`, `productName`, `costPrice`, `salePrice`) VALUES
(1, 1, 'Islak Hamburger', '3', '5'),
(2, 3, 'ürünTest', '1', '2');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Shift`
--

CREATE TABLE `Shift` (
  `shiftDay` text COLLATE utf8_turkish_ci NOT NULL,
  `groupNo` int(11) NOT NULL,
  `shiftRange` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ShiftGroup`
--

CREATE TABLE `ShiftGroup` (
  `groupNo` int(11) NOT NULL,
  `employeeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `soldproduct`
--

CREATE TABLE `soldproduct` (
  `soldID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `date` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci ROW_FORMAT=COMPACT;

--
-- Tablo döküm verisi `soldproduct`
--

INSERT INTO `soldproduct` (`soldID`, `productID`, `date`, `count`) VALUES
(1, 1, '2017/08/22', 6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `Statu`
--

CREATE TABLE `Statu` (
  `statuID` int(11) NOT NULL,
  `statuName` varchar(100) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `Statu`
--

INSERT INTO `Statu` (`statuID`, `statuName`) VALUES
(1, 'eski çalışan'),
(2, 'yönetici');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `TableCount`
--

CREATE TABLE `TableCount` (
  `tableCount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `TableCount`
--

INSERT INTO `TableCount` (`tableCount`) VALUES
(20);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `User`
--

CREATE TABLE `User` (
  `employeeTcNo` text COLLATE utf8_turkish_ci NOT NULL,
  `employeePasswd` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `User`
--

INSERT INTO `User` (`employeeTcNo`, `employeePasswd`) VALUES
('0', '0'),
('33322235126', '1');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `Category`
--
ALTER TABLE `Category`
  ADD PRIMARY KEY (`categoryID`),
  ADD UNIQUE KEY `categoryID` (`categoryID`);

--
-- Tablo için indeksler `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`employeeID`),
  ADD UNIQUE KEY `employeeID` (`employeeID`);

--
-- Tablo için indeksler `Log`
--
ALTER TABLE `Log`
  ADD PRIMARY KEY (`logID`);

--
-- Tablo için indeksler `OrderTable`
--
ALTER TABLE `OrderTable`
  ADD PRIMARY KEY (`orderID`),
  ADD UNIQUE KEY `orderID` (`orderID`);

--
-- Tablo için indeksler `Product`
--
ALTER TABLE `Product`
  ADD PRIMARY KEY (`productID`),
  ADD UNIQUE KEY `productID` (`productID`);

--
-- Tablo için indeksler `soldproduct`
--
ALTER TABLE `soldproduct`
  ADD PRIMARY KEY (`soldID`),
  ADD UNIQUE KEY `soldID` (`soldID`);

--
-- Tablo için indeksler `Statu`
--
ALTER TABLE `Statu`
  ADD PRIMARY KEY (`statuID`),
  ADD UNIQUE KEY `statuID` (`statuID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `Category`
--
ALTER TABLE `Category`
  MODIFY `categoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Tablo için AUTO_INCREMENT değeri `Employee`
--
ALTER TABLE `Employee`
  MODIFY `employeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Tablo için AUTO_INCREMENT değeri `Log`
--
ALTER TABLE `Log`
  MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- Tablo için AUTO_INCREMENT değeri `OrderTable`
--
ALTER TABLE `OrderTable`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Tablo için AUTO_INCREMENT değeri `Product`
--
ALTER TABLE `Product`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Tablo için AUTO_INCREMENT değeri `soldproduct`
--
ALTER TABLE `soldproduct`
  MODIFY `soldID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Tablo için AUTO_INCREMENT değeri `Statu`
--
ALTER TABLE `Statu`
  MODIFY `statuID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
