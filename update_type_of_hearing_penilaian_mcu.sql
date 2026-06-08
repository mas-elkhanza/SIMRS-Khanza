-- Tambahkan keterangan jenis pelindung pendengaran dari UI RMMCU.

USE `sik2023_server`;

ALTER TABLE `penilaian_mcu`
  ADD COLUMN `type_of_hearing` varchar(255) DEFAULT NULL
  AFTER `audiometri_ear_protection_worn_always`;
