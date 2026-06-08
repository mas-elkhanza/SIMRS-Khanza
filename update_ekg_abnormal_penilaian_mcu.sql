-- Status ECG disimpan di conc_ecg, sedangkan keterangannya disimpan di ekg.
-- Kolom ekg harus berupa teks karena ecg_abnormal dapat berisi keterangan bebas.

USE `sik2023_server`;

ALTER TABLE `penilaian_mcu`
  MODIFY `conc_ecg` enum('Normal','Abnormal') DEFAULT NULL,
  MODIFY `ekg` varchar(255) DEFAULT NULL;
