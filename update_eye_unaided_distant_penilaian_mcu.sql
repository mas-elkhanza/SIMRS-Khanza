-- Ubah nilai unaided distant mata kiri/kanan dari pilihan Yes/No menjadi teks visus.

USE `sik2023_server`;

ALTER TABLE `penilaian_mcu`
  MODIFY `eye_unaided_distant_l` varchar(20) DEFAULT NULL,
  MODIFY `eye_unaided_distant_r` varchar(20) DEFAULT NULL;
