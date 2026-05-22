-- Tambahan index dan relasi untuk tabel penilaian_mcu.
-- Jalankan setelah update_tabel_penilaian_mcu.sql.
--
-- Catatan:
-- - pasien.no_rkm_medis pada struktur Khanza adalah varchar(15).
-- - no_rawat pada reg_periksa adalah varchar(17).
-- - FK dibuat hanya jika tabel/kolom rujukan ada, FK belum ada, dan tidak ada data orphan.

SET @db = DATABASE();

-- Kolom dari textbox Year.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'year'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `year` varchar(10) DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `year` varchar(10) DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'year'
    )
    AND EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'tanggal'
    ),
    'UPDATE `penilaian_mcu` SET `year` = YEAR(`tanggal`) WHERE (`year` IS NULL OR `year` = '''') AND `tanggal` IS NOT NULL',
    'SELECT ''Skip isi year: kolom year/tanggal tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Suku bangsa di form memakai nama_suku_bangsa, bukan id pasien.suku_bangsa.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'suku_bangsa'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `suku_bangsa` varchar(255) DEFAULT NULL',
    'SELECT ''Skip modify suku_bangsa: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = @db
          AND table_name = 'suku_bangsa'
    )
    AND EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'suku_bangsa'
          AND column_name = 'id'
    )
    AND EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'suku_bangsa'
          AND column_name = 'nama_suku_bangsa'
    )
    AND EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'suku_bangsa'
    ),
    'UPDATE `penilaian_mcu` pm INNER JOIN `suku_bangsa` sb ON CAST(sb.`id` AS CHAR) = pm.`suku_bangsa` SET pm.`suku_bangsa` = sb.`nama_suku_bangsa` WHERE TRIM(COALESCE(pm.`suku_bangsa`, '''')) <> ''''',
    'SELECT ''Skip migrasi suku_bangsa: tabel/kolom tidak lengkap'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Kolom ini hanya judul di form, bukan data yang disimpan.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_type_of_hearing_worn'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `audiometri_type_of_hearing_worn`',
    'SELECT ''Skip drop audiometri_type_of_hearing_worn: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Audiometri baru memakai 4 grup kolom: left AB, left AC, right AB, right AC.
-- Kolom lama dimigrasikan ke nama baru bila masih ada.
DROP PROCEDURE IF EXISTS `migrate_penilaian_mcu_column`;
DELIMITER $$
CREATE PROCEDURE `migrate_penilaian_mcu_column`(
    IN old_name varchar(128),
    IN new_name varchar(128),
    IN type_def varchar(255)
)
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'penilaian_mcu'
          AND column_name = old_name
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'penilaian_mcu'
          AND column_name = new_name
    ) THEN
        SET @sql = CONCAT('ALTER TABLE `penilaian_mcu` CHANGE `', old_name, '` `', new_name, '` ', type_def);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    ELSEIF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'penilaian_mcu'
          AND column_name = new_name
    ) THEN
        SET @sql = CONCAT('ALTER TABLE `penilaian_mcu` ADD COLUMN `', new_name, '` ', type_def);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    ELSEIF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'penilaian_mcu'
          AND column_name = old_name
    ) THEN
        SET @sql = CONCAT(
            'UPDATE `penilaian_mcu` SET `', new_name, '` = `', old_name, '` ',
            'WHERE (`', new_name, '` IS NULL OR `', new_name, '` = '''') ',
            'AND `', old_name, '` IS NOT NULL AND CHAR_LENGTH(`', old_name, '`) > 0'
        );
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        SET @sql = CONCAT('ALTER TABLE `penilaian_mcu` DROP COLUMN `', old_name, '`');
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END$$
DELIMITER ;

CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_500','audiometri_left_ear_500_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1000','audiometri_left_ear_1000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1500','audiometri_left_ear_1500_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_2000','audiometri_left_ear_2000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_3000','audiometri_left_ear_3000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_4000','audiometri_left_ear_4000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_5000','audiometri_left_ear_5000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_6000','audiometri_left_ear_6000_AB','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_501','audiometri_left_ear_500_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1001','audiometri_left_ear_1000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1501','audiometri_left_ear_1500_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_2001','audiometri_left_ear_2000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_3001','audiometri_left_ear_3000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_4001','audiometri_left_ear_4000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_5001','audiometri_left_ear_5000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_6001','audiometri_left_ear_6000_AC','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_502','audiometri_right_ear_500_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1002','audiometri_right_ear_1000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1502','audiometri_right_ear_1500_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_2002','audiometri_right_ear_2000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_3002','audiometri_right_ear_3000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_4002','audiometri_right_ear_4000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_5002','audiometri_right_ear_5000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_6002','audiometri_right_ear_6000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_500','audiometri_right_ear_500_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_1000','audiometri_right_ear_1000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_1500','audiometri_right_ear_1500_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_2000','audiometri_right_ear_2000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_3000','audiometri_right_ear_3000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_4000','audiometri_right_ear_4000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_5000','audiometri_right_ear_5000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_right_ear_6000','audiometri_right_ear_6000_ab','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_503','audiometri_right_ear_500_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1003','audiometri_right_ear_1000_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_1503','audiometri_right_ear_1500_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_2003','audiometri_right_ear_2000_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_3003','audiometri_right_ear_3000_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_4003','audiometri_right_ear_4000_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_5003','audiometri_right_ear_5000_ac','TEXT NULL');
CALL `migrate_penilaian_mcu_column`('audiometri_left_ear_6003','audiometri_right_ear_6000_ac','TEXT NULL');

DROP PROCEDURE IF EXISTS `migrate_penilaian_mcu_column`;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_left_ear_7000'
    ),
    'SELECT ''Skip add audiometri_left_ear_7000: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_left_ear_7000` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_left_ear_8000'
    ),
    'SELECT ''Skip add audiometri_left_ear_8000: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_left_ear_8000` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_left_ear_hl_percent'
    ),
    'SELECT ''Skip add audiometri_left_ear_hl_percent: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_left_ear_hl_percent` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_right_ear_7000'
    ),
    'SELECT ''Skip add audiometri_right_ear_7000: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_right_ear_7000` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_right_ear_8000'
    ),
    'SELECT ''Skip add audiometri_right_ear_8000: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_right_ear_8000` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'audiometri_right_ear_hl_percent'
    ),
    'SELECT ''Skip add audiometri_right_ear_hl_percent: kolom sudah ada'' AS info',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `audiometri_right_ear_hl_percent` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Kolom unfit_comment_2 dan unfit_comment_3 tidak dipakai.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'unfit_comment_2'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `unfit_comment_2`',
    'SELECT ''Skip drop unfit_comment_2: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'unfit_comment_3'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `unfit_comment_3`',
    'SELECT ''Skip drop unfit_comment_3: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Samakan enum sesuai struktur form/dump terbaru.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'mcu_group'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `mcu_group` ENUM(''Grup A'',''Grup B'',''Grup C'') DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `mcu_group` ENUM(''Grup A'',''Grup B'',''Grup C'') DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'posisi_kerja'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `posisi_kerja` VARCHAR(40) DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `posisi_kerja` VARCHAR(40) DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `posisi_kerja` = CASE LOWER(TRIM(COALESCE(`posisi_kerja`, '')))
    WHEN 'pre employment' THEN 'Pre employment'
    WHEN 'preplacement' THEN 'Preplacement'
    WHEN 'periodic' THEN 'Periodic'
    ELSE NULLIF(TRIM(`posisi_kerja`), '')
END;

UPDATE `penilaian_mcu`
SET `posisi_kerja` = NULL
WHERE `posisi_kerja` NOT IN ('Pre employment', 'Preplacement', 'Periodic');

ALTER TABLE `penilaian_mcu`
MODIFY `posisi_kerja` ENUM('Pre employment','Preplacement','Periodic') DEFAULT NULL;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'jk'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `jk` VARCHAR(20) DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `jk` VARCHAR(20) DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `jk` = CASE LOWER(TRIM(COALESCE(`jk`, '')))
    WHEN 'l' THEN 'Laki laki'
    WHEN 'laki-laki' THEN 'Laki laki'
    WHEN 'laki laki' THEN 'Laki laki'
    WHEN 'p' THEN 'Perempuan'
    WHEN 'perempuan' THEN 'Perempuan'
    ELSE NULLIF(TRIM(`jk`), '')
END;

UPDATE `penilaian_mcu`
SET `jk` = NULL
WHERE `jk` NOT IN ('Laki laki', 'Perempuan');

ALTER TABLE `penilaian_mcu`
MODIFY `jk` ENUM('Laki laki','Perempuan') DEFAULT NULL;

-- ConcEcg sekarang mengikuti combobox cbConcEcg (Normal/Abnormal).
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'conc_ecg'
    ),
    'UPDATE `penilaian_mcu` SET `conc_ecg` = IF(LOWER(TRIM(`conc_ecg`)) = ''normal'', ''Normal'', ''Abnormal'') WHERE LOWER(TRIM(COALESCE(`conc_ecg`, ''''))) IN (''normal'', ''abnormal'')',
    'SELECT ''Skip normalize conc_ecg: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'conc_ecg'
    )
    AND NOT EXISTS (
        SELECT 1
        FROM `penilaian_mcu`
        WHERE TRIM(COALESCE(`conc_ecg`, '')) <> ''
          AND LOWER(TRIM(`conc_ecg`)) NOT IN ('normal', 'abnormal')
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `conc_ecg` ENUM(''Normal'',''Abnormal'') DEFAULT NULL',
    'SELECT ''Skip modify conc_ecg enum: kolom tidak ada atau masih ada data lama selain normal/abnormal'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Eye color blindless mengikuti pilihan combobox: Color Blind, Normal, Partial Color Blind.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'eye_color_blindless'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `eye_color_blindless` VARCHAR(40) DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `eye_color_blindless` VARCHAR(40) DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `eye_color_blindless` = CASE LOWER(TRIM(COALESCE(`eye_color_blindless`, '')))
    WHEN 'color blind' THEN 'Color Blind'
    WHEN 'normal' THEN 'Normal'
    WHEN 'red green absen' THEN 'Partial Color Blind'
    WHEN 'partial colod blind' THEN 'Partial Color Blind'
    WHEN 'partial color blind' THEN 'Partial Color Blind'
    ELSE NULLIF(TRIM(`eye_color_blindless`), '')
END;

UPDATE `penilaian_mcu`
SET `eye_color_blindless` = NULL
WHERE `eye_color_blindless` NOT IN ('Color Blind', 'Normal', 'Partial Color Blind');

ALTER TABLE `penilaian_mcu`
MODIFY `eye_color_blindless` ENUM('Color Blind','Normal','Partial Color Blind') DEFAULT NULL;

-- medically_fit disimpan sebagai enum; fit_with_restrictions disimpan sebagai teks
-- agar pilihan Specify bisa menyimpan isi variabel specify.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'medically_fit'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `medically_fit` VARCHAR(80) DEFAULT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `medically_fit` VARCHAR(80) DEFAULT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'fit_with_restrictions'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `fit_with_restrictions` TEXT NULL',
    'ALTER TABLE `penilaian_mcu` ADD COLUMN `fit_with_restrictions` TEXT NULL'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `medically_fit` = CASE LOWER(TRIM(COALESCE(`medically_fit`, '')))
    WHEN 'yes' THEN 'Medically Fit'
    WHEN 'true' THEN 'Medically Fit'
    WHEN '1' THEN 'Medically Fit'
    WHEN 'medically fit' THEN 'Medically Fit'
    WHEN 'presently has minor medical problem' THEN 'Presently has minor medical problem'
    WHEN 'unfit' THEN 'Unfit'
    ELSE NULLIF(TRIM(`medically_fit`), '')
END;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'presently_has_minor_medical_problem'
    ),
    'UPDATE `penilaian_mcu` SET `medically_fit` = ''Presently has minor medical problem'' WHERE LOWER(TRIM(COALESCE(`presently_has_minor_medical_problem`, ''''))) IN (''yes'', ''true'', ''1'')',
    'SELECT ''Skip migrate presently_has_minor_medical_problem: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `medically_fit` = NULL
WHERE `medically_fit` NOT IN ('Medically Fit', 'Presently has minor medical problem', 'Unfit');

ALTER TABLE `penilaian_mcu`
MODIFY `medically_fit` ENUM('Medically Fit','Presently has minor medical problem','Unfit') DEFAULT NULL;

UPDATE `penilaian_mcu`
SET `fit_with_restrictions` = CASE LOWER(TRIM(COALESCE(`fit_with_restrictions`, '')))
    WHEN 'medically fit but has following restrictions' THEN 'Medically fit but has following restrictions'
    WHEN 'work duties will be restricted' THEN 'Work duties will be restricted'
    WHEN 'specify' THEN 'Specify'
    WHEN 'specift' THEN 'Specify'
    ELSE NULLIF(TRIM(`fit_with_restrictions`), '')
END;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'medically_fit_but_has_following_restrictions'
    ),
    'UPDATE `penilaian_mcu` SET `fit_with_restrictions` = ''Medically fit but has following restrictions'' WHERE LOWER(TRIM(COALESCE(`medically_fit_but_has_following_restrictions`, ''''))) IN (''yes'', ''true'', ''1'')',
    'SELECT ''Skip migrate medically_fit_but_has_following_restrictions: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'work_duties_will_be_restricted'
    ),
    'UPDATE `penilaian_mcu` SET `fit_with_restrictions` = ''Work duties will be restricted'' WHERE LOWER(TRIM(COALESCE(`work_duties_will_be_restricted`, ''''))) IN (''yes'', ''true'', ''1'')',
    'SELECT ''Skip migrate work_duties_will_be_restricted: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'fit_with_restrictions_specify'
    ),
    'UPDATE `penilaian_mcu` SET `fit_with_restrictions` = TRIM(`fit_with_restrictions_specify`) WHERE TRIM(COALESCE(`fit_with_restrictions_specify`, '''')) <> ''''',
    'SELECT ''Skip migrate fit_with_restrictions_specify: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE `penilaian_mcu`
SET `fit_with_restrictions` = NULL
WHERE TRIM(COALESCE(`fit_with_restrictions`, '')) = '';

ALTER TABLE `penilaian_mcu`
MODIFY `fit_with_restrictions` TEXT NULL;

-- Kolom pecahan fit tidak dipakai lagi karena nilainya sudah masuk kolom utama.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'presently_has_minor_medical_problem'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `presently_has_minor_medical_problem`',
    'SELECT ''Skip drop presently_has_minor_medical_problem: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'medically_fit_but_has_following_restrictions'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `medically_fit_but_has_following_restrictions`',
    'SELECT ''Skip drop medically_fit_but_has_following_restrictions: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'work_duties_will_be_restricted'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `work_duties_will_be_restricted`',
    'SELECT ''Skip drop work_duties_will_be_restricted: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'fit_with_restrictions_specify'
    ),
    'ALTER TABLE `penilaian_mcu` DROP COLUMN `fit_with_restrictions_specify`',
    'SELECT ''Skip drop fit_with_restrictions_specify: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'unfit_comment_1'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `unfit_comment_1` TEXT NULL',
    'SELECT ''Skip modify unfit_comment_1: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'blood_group'
    ),
    'UPDATE `penilaian_mcu` SET `blood_group` = UPPER(TRIM(`blood_group`)) WHERE `blood_group` IS NOT NULL',
    'SELECT ''Skip update blood_group: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'blood_group'
    ),
    'UPDATE `penilaian_mcu` SET `blood_group` = NULL WHERE `blood_group` NOT IN (''A'',''B'',''AB'',''O'')',
    'SELECT ''Skip clean blood_group: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'blood_group'
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `blood_group` ENUM(''A'',''B'',''AB'',''O'') DEFAULT NULL',
    'SELECT ''Skip modify blood_group: kolom tidak ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Semua field yang berasal dari checkbox/combobox Yes-No disimpan sebagai enum yes/no
-- dan default database-nya dibuat no.
CREATE TEMPORARY TABLE IF NOT EXISTS `tmp_penilaian_mcu_checkbox_columns` (
    `column_name` varchar(128) NOT NULL,
    PRIMARY KEY (`column_name`)
);

TRUNCATE TABLE `tmp_penilaian_mcu_checkbox_columns`;

INSERT INTO `tmp_penilaian_mcu_checkbox_columns` (`column_name`) VALUES
('job_involves_driving_or_operating_mobile_equipment'),
('job_involves_working_at_heights'),
('job_involves_clerical_office_based_or_administrative'),
('job_involves_requires_colour_vision'),
('job_involves_potential_dust_exposure'),
('job_involves_catering_staff_including_food_handlers'),
('job_involves_exposing_to_other_potential_dangerous'),
('med_hist_head_injury_or_contussion'),
('med_hist_fainting_blackouts_epilepsy'),
('med_hist_visual_changes'),
('med_hist_hearing_loss'),
('med_hist_nose_sinus_throat_trouble_more_4_weeks'),
('med_hist_gynaecological_problems'),
('med_hist_chronic_skin_problem'),
('med_hist_chronic_diarrhea'),
('med_hist_anorexia_more_4_weeks'),
('med_hist_gastritis'),
('med_hist_jaundice_hepatitis'),
('med_hist_chronic_cough_more_4_weeks'),
('med_hist_haemorhoid'),
('med_hist_chronic_abdominal_pain'),
('med_hist_diabetes'),
('med_hist_asthma'),
('med_hist_allergies'),
('med_hist_tuberculosis_bronchitis'),
('med_hist_psychiatric_disorder'),
('med_hist_sexual_transmitted_diseases'),
('med_hist_unusual_change_of_weight_more_5kg_per_month'),
('med_hist_hypertension'),
('med_hist_chest_pain_heart_disease'),
('med_hist_malaria_tropical_disease'),
('med_hist_surgery_operation'),
('med_hist_back_pain_more_4_weeks'),
('med_hist_thypoid_fever'),
('med_hist_swollen_or_painful_joints'),
('med_hist_kidney_problem_urinary_stones'),
('med_hist_other_chronical_diseases'),
('audiometri_tinitus_never'),
('audiometri_tinitus_previously'),
('audiometri_tinitus_rarely'),
('audiometri_tinitus_often'),
('audiometri_tinitus_always'),
('audiometri_ear_protection_worn_never'),
('audiometri_ear_protection_worn_previously'),
('audiometri_ear_protection_worn_rarely'),
('audiometri_ear_protection_worn_often'),
('audiometri_ear_protection_worn_always'),
('conclusion_requires_spectacles'),
('conclusion_colour_blindness'),
('conclusion_respiratory_problem'),
('conclusion_impaired_hearing'),
('conclusion_vertigo');

SET SESSION group_concat_max_len = 100000;

SELECT GROUP_CONCAT(
    CONCAT(
        '`', c.column_name, '` = IF(LOWER(TRIM(COALESCE(`', c.column_name,
        '`, ''''))) IN (''yes'', ''true'', ''1''), ''Yes'', ''No'')'
    )
    SEPARATOR ', '
)
INTO @set_checkbox_sql
FROM `tmp_penilaian_mcu_checkbox_columns` tmp
INNER JOIN information_schema.columns c ON c.table_schema = @db
    AND c.table_name = 'penilaian_mcu'
    AND c.column_name = tmp.column_name;

SET @sql = IF(
    @set_checkbox_sql IS NULL OR @set_checkbox_sql = '',
    'SELECT ''Skip update checkbox columns: tidak ada kolom yang cocok'' AS info',
    CONCAT('UPDATE `penilaian_mcu` SET ', @set_checkbox_sql)
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT GROUP_CONCAT(
    CONCAT('MODIFY `', c.column_name, '` ENUM(''Yes'',''No'') NOT NULL DEFAULT ''No''')
    SEPARATOR ', '
)
INTO @modify_checkbox_sql
FROM `tmp_penilaian_mcu_checkbox_columns` tmp
INNER JOIN information_schema.columns c ON c.table_schema = @db
    AND c.table_name = 'penilaian_mcu'
    AND c.column_name = tmp.column_name;

SET @sql = IF(
    @modify_checkbox_sql IS NULL OR @modify_checkbox_sql = '',
    'SELECT ''Skip modify checkbox columns: tidak ada kolom yang cocok'' AS info',
    CONCAT('ALTER TABLE `penilaian_mcu` ', @modify_checkbox_sql)
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

DROP TEMPORARY TABLE IF EXISTS `tmp_penilaian_mcu_checkbox_columns`;

-- Samakan nomor rekam medis MCU dengan data registrasi.
UPDATE `penilaian_mcu` pm
INNER JOIN `reg_periksa` rp ON rp.`no_rawat` = pm.`no_rawat`
SET pm.`no_rkm_medis` = rp.`no_rkm_medis`
WHERE pm.`no_rkm_medis` IS NULL
   OR pm.`no_rkm_medis` = ''
   OR pm.`no_rkm_medis` <> rp.`no_rkm_medis`;

UPDATE `penilaian_mcu`
SET `no_rkm_medis` = NULL
WHERE `no_rkm_medis` = '';

-- Tampilkan contoh data yang perlu dibersihkan manual sebelum FK dibuat.
SELECT 'orphan_reg_periksa' AS `cek`, pm.`no_rawat`
FROM `penilaian_mcu` pm
LEFT JOIN `reg_periksa` rp ON rp.`no_rawat` = pm.`no_rawat`
WHERE rp.`no_rawat` IS NULL
LIMIT 20;

SELECT 'orphan_pasien' AS `cek`, pm.`no_rawat`, pm.`no_rkm_medis`
FROM `penilaian_mcu` pm
LEFT JOIN `pasien` ps ON ps.`no_rkm_medis` = pm.`no_rkm_medis`
WHERE pm.`no_rkm_medis` IS NOT NULL
  AND ps.`no_rkm_medis` IS NULL
LIMIT 20;

-- Sesuaikan panjang no_rkm_medis agar bisa direlasikan ke pasien.no_rkm_medis.
SET @sql = IF(
    NOT EXISTS (
        SELECT 1
        FROM `penilaian_mcu`
        WHERE `no_rkm_medis` IS NOT NULL
          AND CHAR_LENGTH(`no_rkm_medis`) > 15
    ),
    'ALTER TABLE `penilaian_mcu` MODIFY `no_rkm_medis` varchar(15) DEFAULT NULL',
    'SELECT ''Skip modify no_rkm_medis: masih ada nilai lebih dari 15 karakter'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Pastikan index pendukung ada. PRIMARY KEY no_rawat akan membuat index no_rawat otomatis.
SET @sql = IF(
    NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'no_rawat'
          AND seq_in_index = 1
    ),
    'ALTER TABLE `penilaian_mcu` ADD INDEX `idx_penilaian_mcu_no_rawat` (`no_rawat`)',
    'SELECT ''Skip index no_rawat: index kiri sudah ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'no_rkm_medis'
          AND seq_in_index = 1
    ),
    'ALTER TABLE `penilaian_mcu` ADD INDEX `idx_penilaian_mcu_no_rkm_medis` (`no_rkm_medis`)',
    'SELECT ''Skip index no_rkm_medis: index kiri sudah ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND index_name = 'idx_penilaian_mcu_tanggal_rawat'
    ),
    'ALTER TABLE `penilaian_mcu` ADD INDEX `idx_penilaian_mcu_tanggal_rawat` (`tanggal`, `no_rawat`)',
    'SELECT ''Skip index tanggal_rawat: index sudah ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    NOT EXISTS (
        SELECT 1
        FROM information_schema.statistics
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'kd_dokter'
          AND seq_in_index = 1
    ),
    'ALTER TABLE `penilaian_mcu` ADD INDEX `idx_penilaian_mcu_kd_dokter` (`kd_dokter`)',
    'SELECT ''Skip index kd_dokter: index kiri sudah ada'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Relasi ke reg_periksa jika belum ada.
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'reg_periksa'
          AND column_name = 'no_rawat'
    )
    AND NOT EXISTS (
        SELECT 1
        FROM `penilaian_mcu` pm
        LEFT JOIN `reg_periksa` rp ON rp.`no_rawat` = pm.`no_rawat`
        WHERE rp.`no_rawat` IS NULL
    )
    AND NOT EXISTS (
        SELECT 1
        FROM information_schema.key_column_usage
        WHERE constraint_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'no_rawat'
          AND referenced_table_name = 'reg_periksa'
          AND referenced_column_name = 'no_rawat'
    ),
    'ALTER TABLE `penilaian_mcu` ADD CONSTRAINT `fk_penilaian_mcu_reg_periksa` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE',
    'SELECT ''Skip FK reg_periksa: sudah ada, tabel tidak ada, atau masih ada orphan no_rawat'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Relasi langsung ke pasien jika kolom no_rkm_medis sudah cocok varchar(15).
SET @sql = IF(
    EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'pasien'
          AND column_name = 'no_rkm_medis'
    )
    AND EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'no_rkm_medis'
          AND data_type = 'varchar'
          AND character_maximum_length = 15
    )
    AND NOT EXISTS (
        SELECT 1
        FROM `penilaian_mcu` pm
        LEFT JOIN `pasien` ps ON ps.`no_rkm_medis` = pm.`no_rkm_medis`
        WHERE pm.`no_rkm_medis` IS NOT NULL
          AND ps.`no_rkm_medis` IS NULL
    )
    AND NOT EXISTS (
        SELECT 1
        FROM information_schema.key_column_usage
        WHERE constraint_schema = @db
          AND table_name = 'penilaian_mcu'
          AND column_name = 'no_rkm_medis'
          AND referenced_table_name = 'pasien'
          AND referenced_column_name = 'no_rkm_medis'
    ),
    'ALTER TABLE `penilaian_mcu` ADD CONSTRAINT `fk_penilaian_mcu_pasien` FOREIGN KEY (`no_rkm_medis`) REFERENCES `pasien` (`no_rkm_medis`) ON UPDATE CASCADE',
    'SELECT ''Skip FK pasien: sudah ada, kolom belum cocok, tabel tidak ada, atau masih ada orphan no_rkm_medis'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
