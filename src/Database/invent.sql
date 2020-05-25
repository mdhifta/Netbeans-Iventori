
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
--
--

-- --------------------------------------------------------

--
-- 
--

CREATE TABLE `barang` (
  `kode_brg` VARCHAR(10) DEFAULT NULL,
  `nama_brg` VARCHAR(50) DEFAULT NULL,
  `id_sup` VARCHAR(10) DEFAULT NULL,
  `stok` INT(11) DEFAULT NULL,
  `harga` BIGINT(20) DEFAULT NULL,
  `jenis` VARCHAR(20) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_brg`, `nama_brg`, `id_sup`, `stok`, `harga`, `jenis`) VALUES
('IND003', 'Indomie Goreng Rendang', 'INDF', 50, 3000, 'mie'),
('IND040', 'Kikky Jelly Drink', 'INDF', 10, NULL, 'minuman'),
('UNLV', 'Pepsoden 30G', 'UNLV', 10, NULL, 'pasta gigi');

-- --------------------------------------------------------

--
-- Table structure for table `data_login`
--

CREATE TABLE `data_login` (
  `usernames` VARCHAR(16) NOT NULL,
  `passwords` VARCHAR(16) DEFAULT NULL,
  `nama` VARCHAR(50) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data_login`
--

INSERT INTO `data_login` (`usernames`, `passwords`, `nama`) VALUES
('test', 'test', 'baybaydes');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_sup` VARCHAR(10) NOT NULL,
  `nama_sup` VARCHAR(50) DEFAULT NULL,
  `alamat_sup` VARCHAR(100) DEFAULT NULL,
  `no_hp_sup` VARCHAR(15) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_sup`, `nama_sup`, `alamat_sup`, `no_hp_sup`) VALUES
('ILM', 'Indo Lautan Makmur', 'Serang, Banten', '675876'),
('INDF', 'Indofood', 'Jakarta', '40298'),
('PNDI', 'Pond\'s Indonesia', 'Citayam, Jawa Barat', '798097'),
('UNLV', 'Inilever Indonesia', 'Jakarta Pusat', '420990');

-- --------------------------------------------------------

--
-- Table structure for table `tansaksi`
--

CREATE TABLE `tansaksi` (
  `no_trx` VARCHAR(20) DEFAULT NULL,
  `id_sup` VARCHAR(10) DEFAULT NULL,
  `kode_brg` VARCHAR(10) DEFAULT NULL,
  `stok` INT(11) DEFAULT NULL,
  `tgl_trx` DATE DEFAULT NULL,
  `jns_trx` ENUM('masuk','keluar') DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD KEY `FK_brg_sup` (`id_sup`);

--
-- Indexes for table `data_login`
--
ALTER TABLE `data_login`
  ADD PRIMARY KEY (`usernames`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_sup`);


-- Constraints for dumped tables
--


--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `FK_brg_sup` FOREIGN KEY (`id_sup`) REFERENCES `supplier` (`id_sup`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

#trigger
USE invent;
#SELECT * FROM data_login ;
#SELECT * FROM data_login WHERE usernames='al' AND passwords='roz';
#DROP TABLE backup_p;
CREATE TABLE backup_p (
kode_barang VARCHAR(10),
nama_barang VARCHAR(50),
id_suplier VARCHAR(10),
jumlah_msk INT(11),
harga BIGINT(20),
jenis VARCHAR(20),
tgl_wkt DATETIME
);

DELIMITER$$
CREATE TRIGGER backup_pemasukan AFTER UPDATE ON barang FOR EACH ROW
BEGIN
INSERT INTO backup_p VALUES(old.kode_brg, old.nama_brg, old.id_sup, (NEW.stok-OLD.stok), old.harga, old.jenis, NOW());
END$$
DELIMITER;

UPDATE barang SET stok=5 WHERE kode_brg='UNLV';

SELECT * FROM backup_p;
