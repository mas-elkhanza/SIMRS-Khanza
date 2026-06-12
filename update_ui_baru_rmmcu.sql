-- Bangun ulang tabel penilaian_mcu agar sesuai dengan PENILAIAN_MCU_COLUMNS
-- pada src/rekammedis/RMMCU.java (231 kolom unik).
--
-- Tabel lama tidak dihapus. Tabel tersebut diganti nama menjadi
-- penilaian_mcu_backup_ui_YYYYMMDD_HHMMSS_microseconds, kemudian semua
-- kolom yang namanya masih sama disalin ke tabel baru.
--
-- Jalankan seluruh skrip sekaligus setelah backup database.

USE `sik2023_server`;

SET @db = DATABASE();
SET SESSION group_concat_max_len = 1000000;
SET @old_table_exists = (
    SELECT COUNT(*)
    FROM information_schema.tables
    WHERE table_schema = @db
      AND table_name = 'penilaian_mcu'
);
SET @backup_table = CONCAT(
    'penilaian_mcu_backup_ui_',
    DATE_FORMAT(NOW(6), '%Y%m%d_%H%i%s_%f')
);

-- Lepas foreign key tabel lama agar nama constraint dapat dipakai tabel baru.
SELECT GROUP_CONCAT(
    CONCAT('DROP FOREIGN KEY `', constraint_name, '`')
    ORDER BY constraint_name
    SEPARATOR ', '
)
INTO @drop_fk
FROM information_schema.key_column_usage
WHERE table_schema = @db
  AND table_name = 'penilaian_mcu'
  AND referenced_table_name IS NOT NULL;

SET @sql = IF(
    @old_table_exists = 1 AND @drop_fk IS NOT NULL,
    CONCAT('ALTER TABLE `penilaian_mcu` ', @drop_fk),
    'SELECT ''Tidak ada foreign key tabel lama yang perlu dilepas'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = IF(
    @old_table_exists = 1,
    CONCAT('RENAME TABLE `penilaian_mcu` TO `', @backup_table, '`'),
    'SELECT ''Tabel penilaian_mcu belum ada; membuat tabel baru'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

CREATE TABLE `penilaian_mcu` (
    `no_rawat` varchar(17) NOT NULL,
    `tanggal` datetime NOT NULL,
    `year` varchar(4) DEFAULT NULL,
    `kd_dokter` varchar(20) NOT NULL,
    `kd_petugas` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`no_rawat`),
    KEY `kd_dokter` (`kd_dokter`),
    KEY `kd_petugas` (`kd_petugas`),
    KEY `tanggal` (`tanggal`)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TEMPORARY TABLE IF EXISTS `tmp_penilaian_mcu_columns`;
CREATE TEMPORARY TABLE `tmp_penilaian_mcu_columns` (
    `urutan` int unsigned NOT NULL AUTO_INCREMENT,
    `column_name` varchar(128) NOT NULL,
    `column_definition` varchar(1000) NOT NULL DEFAULT 'TEXT NULL',
    PRIMARY KEY (`urutan`),
    UNIQUE KEY `column_name` (`column_name`)
);

INSERT INTO `tmp_penilaian_mcu_columns` (`column_name`) VALUES
('note1'),('nama_pasien'),('surname'),('mcu_group'),('dass_21'),('phy_exam'),('conc_lab'),('conc_radiologi'),
('conc_ecg'),('conc_spirometry'),('conc_audiometry'),('kesimpulan1'),('no_rkm_medis'),('tmp_lahir'),('tgl_lahir'),('jk'),
('no_tlp'),('suku_bangsa'),('stts_nikah'),('doe'),('yoe'),('job_title'),('activities'),('hobby'),('other_job'),('posisi_kerja'),
('job_involves_driving_or_operating_mobile_equipment'),('job_involves_working_at_heights'),('job_involves_clerical_office_based_or_administrative'),
('job_involves_requires_colour_vision'),('job_involves_potential_dust_exposure'),('job_involves_catering_staff_including_food_handlers'),
('job_involves_exposing_to_other_potential_dangerous'),('med_hist_head_injury_or_contussion'),('med_hist_fainting_blackouts_epilepsy'),
('med_hist_visual_changes'),('med_hist_hearing_loss'),('med_hist_nose_sinus_throat_trouble_more_4_weeks'),('med_hist_gynaecological_problems'),
('med_hist_chronic_skin_problem'),('med_hist_chronic_diarrhea'),('med_hist_anorexia_more_4_weeks'),('med_hist_gastritis'),
('med_hist_jaundice_hepatitis'),('med_hist_chronic_cough_more_4_weeks'),('med_hist_haemorhoid'),('med_hist_chronic_abdominal_pain'),
('med_hist_diabetes'),('med_hist_asthma'),('med_hist_allergies'),('med_hist_tuberculosis_bronchitis'),('med_hist_psychiatric_disorder'),
('med_hist_sexual_transmitted_diseases'),('med_hist_unusual_change_of_weight_more_5kg_per_month'),('med_hist_hypertension'),
('med_hist_chest_pain_heart_disease'),('med_hist_malaria_tropical_disease'),('med_hist_surgery_operation'),('med_hist_back_pain_more_4_weeks'),
('med_hist_thypoid_fever'),('med_hist_swollen_or_painful_joints'),('med_hist_kidney_problem_urinary_stones'),('med_hist_other_chronical_diseases'),
('family_history_father'),('family_history_mother'),('family_history_siblings'),('family_history_other'),('cigarettes_perday'),('alcohol_gr_week'),
('prescribed_medication'),('prescribed_medication_2'),('any_allergies'),('hb'),('wbc'),('esr'),('bl_group'),('gamaa_gt'),('sgot'),('sgpt'),
('urea'),('creatinin'),('glucose'),('random_glucose'),('total_cholestrol'),('protein'),('blood'),('bilirubin'),('malaria'),('tpha'),
('mantoux_test'),('leukosit'),('lab_others'),('ova'),('culture'),('cysta'),('parasites1'),('pnemunosicosis'),('pnemunosicosis2'),
('ILO_clasification'),('ILO_clasification2'),('oth_abnormal'),('tb1'),('tb2'),('page3_comment'),('td'),('nadi'),('rr'),('tb'),('bb'),
('bmi'),('kasifikasi_bmi'),('laborat'),('radiologi'),('ekg'),('spirometri_vc_1'),('spirometri_vc_2'),('spirometri_vc_3'),
('spirometri_vc_4'),('spirometri_fvc_1'),('spirometri_fvc_2'),('spirometri_fvc_3'),('spirometri_fvc_4'),('spirometri_fev_1_1'),
('spirometri_fev_1_2'),('spirometri_fev_1_3'),('spirometri_fev_1_4'),('spirometri_fev_1_fvc_1'),('spirometri_fev_1_fvc_2'),
('spirometri_fev_1_fvc_3'),('spirometri_fev_1_fvc_4'),('audiometri_tinitus_never'),('audiometri_tinitus_previously'),
('audiometri_tinitus_rarely'),('audiometri_tinitus_often'),('audiometri_tinitus_always'),('audiometri_ear_protection_worn_never'),
('audiometri_ear_protection_worn_previously'),('audiometri_ear_protection_worn_rarely'),('audiometri_ear_protection_worn_often'),
('audiometri_ear_protection_worn_always'),('type_of_hearing'),('audiometri_left_ear_500_AB'),('audiometri_left_ear_1000_AB'),
('audiometri_left_ear_1500_AB'),('audiometri_left_ear_2000_AB'),('audiometri_left_ear_3000_AB'),('audiometri_left_ear_4000_AB'),
('audiometri_left_ear_5000_AB'),('audiometri_left_ear_6000_AB'),('audiometri_left_ear_500_AC'),('audiometri_left_ear_1000_AC'),
('audiometri_left_ear_1500_AC'),('audiometri_left_ear_2000_AC'),('audiometri_left_ear_3000_AC'),('audiometri_left_ear_4000_AC'),
('audiometri_left_ear_5000_AC'),('audiometri_left_ear_6000_AC'),('audiometri_right_ear_500_ab'),('audiometri_right_ear_1000_ab'),
('audiometri_right_ear_1500_ab'),('audiometri_right_ear_2000_ab'),('audiometri_right_ear_3000_ab'),('audiometri_right_ear_4000_ab'),
('audiometri_right_ear_5000_ab'),('audiometri_right_ear_6000_ab'),('audiometri_right_ear_500_ac'),('audiometri_right_ear_1000_ac'),
('audiometri_right_ear_1500_ac'),('audiometri_right_ear_2000_ac'),('audiometri_right_ear_3000_ac'),('audiometri_right_ear_4000_ac'),
('audiometri_right_ear_5000_ac'),('audiometri_right_ear_6000_ac'),('eye_unaided_distant_r'),('eye_unaided_distant_l'),
('eye_glasses_distant_r'),('eye_glasses_distant_l'),('eye_unaided_near_r'),('eye_unaided_near_l'),('eye_glasses_near_r'),
('eye_glasses_near_l'),('eye_night_vision_1'),('eye_night_vision_2'),('eye_brake_test_1'),('eye_brake_test_2'),('eye_color_blindless'),
('visual_fields_left'),('visual_fields_right'),('fundi'),('imunisasi_bcg'),('imunisasi_dpt'),('imunisasi_polio'),('imunisasi_morbili'),
('imunisasi_thyphoid'),('imunisasi_hep_a'),('imunisasi_hep_b'),('imunisasi_tetanus'),('imunisasi_others'),('vertebra_scoliosis'),
('vertebra_kyphosis'),('vertebra_lordosis'),('vertebra_forward_flexion_0_80'),('vertebra_hyperextensi_0_25'),
('vertebra_lateral_flexion_0_20'),('vertebra_heel_walking'),('vertebra_toe_walking'),('vertebra_squats_x3'),('exam_ent_comments'),
('exam_cardio_vascular_system_comments'),('exam_respiratory_system_comments'),('exam_abdomen_comments'),('exam_genito_urinary_system_comments'),
('exam_central_peripheral_nervous_system_comments'),('exam_skin_comments'),('exam_lymph_nodes_comments'),('exam_dental_comments'),
('conclusion_requires_spectacles'),('conclusion_colour_blindness'),('conclusion_respiratory_problem'),('conclusion_impaired_hearing'),
('conclusion_vertigo'),('blood_group'),('medically_fit'),('fit_with_restrictions'),('specify'),('unfit_comment_1');

-- TextArea dan hasil/keterangan panjang.
UPDATE `tmp_penilaian_mcu_columns`
SET `column_definition` = 'TEXT NULL'
WHERE `column_name` IN (
    'note1','dass_21','phy_exam','conc_lab','kesimpulan1','laborat','radiologi',
    'specify','unfit_comment_1'
);

-- Identitas dan isian pendek dengan panjang yang diketahui.
UPDATE `tmp_penilaian_mcu_columns`
SET `column_definition` = CASE
    WHEN `column_name` = 'no_rkm_medis' THEN 'VARCHAR(15) NULL'
    WHEN `column_name` = 'tgl_lahir' THEN 'DATE NULL'
    WHEN `column_name` = 'jk' THEN 'VARCHAR(20) NULL'
    WHEN `column_name` IN ('nama_pasien','surname') THEN 'VARCHAR(100) NULL'
    WHEN `column_name` IN ('tmp_lahir','suku_bangsa','stts_nikah') THEN 'VARCHAR(50) NULL'
    WHEN `column_name` = 'no_tlp' THEN 'VARCHAR(40) NULL'
    WHEN `column_name` IN ('doe','yoe') THEN 'VARCHAR(20) NULL'
    WHEN `column_name` IN ('td','nadi','rr','tb','bb','bmi') THEN 'VARCHAR(20) NULL'
    WHEN `column_name` IN ('eye_unaided_distant_r','eye_unaided_distant_l') THEN 'VARCHAR(20) NULL'
    WHEN `column_name` IN (
        'hb','wbc','esr','bl_group','gamaa_gt','sgot','sgpt','urea','creatinin','glucose',
        'random_glucose','total_cholestrol','protein','blood','bilirubin','malaria','tpha',
        'mantoux_test','leukosit','ova','culture','cysta','parasites1','pnemunosicosis',
        'pnemunosicosis2','ILO_clasification','ILO_clasification2','tb1','tb2'
    ) THEN 'VARCHAR(50) NULL'
    WHEN `column_name` LIKE 'spirometri_%' THEN 'VARCHAR(20) NULL'
    WHEN `column_name` LIKE 'audiometri_left_ear_%'
      OR `column_name` LIKE 'audiometri_right_ear_%' THEN 'VARCHAR(20) NULL'
    WHEN `column_name` IN (
        'visual_fields_left','visual_fields_right','kasifikasi_bmi'
    ) THEN 'VARCHAR(100) NULL'
    ELSE `column_definition`
END;

-- CheckBox pada UI: nilai disimpan oleh nilaiCek() sebagai Yes atau No.
UPDATE `tmp_penilaian_mcu_columns`
SET `column_definition` = 'ENUM(''No'',''Yes'') NULL'
WHERE `column_name` LIKE 'job_involves_%'
   OR `column_name` LIKE 'med_hist_%'
   OR `column_name` LIKE 'audiometri_tinitus_%'
   OR `column_name` LIKE 'audiometri_ear_protection_worn_%'
   OR `column_name` LIKE 'conclusion_%';

-- ComboBox pada UI: ENUM mengikuti tepat pilihan yang tersedia di RMMCU.form.
UPDATE `tmp_penilaian_mcu_columns`
SET `column_definition` = CASE
    WHEN `column_name` = 'mcu_group'
        THEN 'ENUM(''Grup A'',''Grup B'',''Grup C'') NULL'
    WHEN `column_name` = 'stts_nikah'
        THEN 'ENUM(''BELUM MENIKAH'',''MENIKAH'',''JANDA'',''DUDHA'',''JOMBLO'') NULL'
    WHEN `column_name` = 'posisi_kerja'
        THEN 'ENUM(''Pre employment'',''Preplacement'',''Periodic'') NULL'
    WHEN `column_name` = 'conc_ecg'
        THEN 'ENUM(''Normal'',''Abnormal'') NULL'
    WHEN `column_name` IN (
        'eye_glasses_distant_r','eye_glasses_distant_l','eye_unaided_near_r','eye_unaided_near_l',
        'eye_glasses_near_r','eye_glasses_near_l','eye_night_vision_1','eye_night_vision_2',
        'eye_brake_test_1','eye_brake_test_2'
    ) THEN 'ENUM(''No'',''Yes'') NULL'
    WHEN `column_name` = 'eye_color_blindless'
        THEN 'ENUM(''Color Blind'',''Normal'',''Partial Color Blind'') NULL'
    WHEN `column_name` = 'fundi'
        THEN 'ENUM(''Normal'',''Abnormal'') NULL'
    WHEN `column_name` LIKE 'imunisasi_%'
        THEN 'ENUM(''-'',''No'',''Yes'') NULL'
    WHEN `column_name` LIKE 'vertebra_%'
        THEN 'ENUM(''Normal'',''Yes'',''No'') NULL'
    WHEN `column_name` = 'blood_group'
        THEN 'ENUM(''A+'',''A-'',''B+'',''B-'',''AB+'',''AB-'',''O+'',''O-'') NULL'
    WHEN `column_name` = 'medically_fit'
        THEN 'ENUM(''Medically Fit'',''Presently has minor medical problem'',''Unfit'') NULL'
    WHEN `column_name` = 'fit_with_restrictions'
        THEN 'ENUM(''Medically fit but has following restrictions'',''Work duties will be restricted'',''Specify'') NULL'
    ELSE `column_definition`
END;

SELECT GROUP_CONCAT(
    CONCAT('ADD COLUMN `', column_name, '` ', column_definition)
    ORDER BY urutan
    SEPARATOR ', '
)
INTO @add_columns
FROM `tmp_penilaian_mcu_columns`;

SET @sql = CONCAT('ALTER TABLE `penilaian_mcu` ', @add_columns);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Salin data lama hanya untuk kolom yang masih digunakan RMMCU.
SELECT GROUP_CONCAT(
    CONCAT('`', baru.column_name, '`')
    ORDER BY baru.ordinal_position
    SEPARATOR ','
)
INTO @common_columns
FROM information_schema.columns baru
INNER JOIN information_schema.columns lama
    ON lama.table_schema = @db
   AND lama.table_name = @backup_table
   AND lama.column_name = baru.column_name
WHERE baru.table_schema = @db
  AND baru.table_name = 'penilaian_mcu';

SET @sql = IF(
    @old_table_exists = 1 AND @common_columns IS NOT NULL,
    CONCAT(
        'INSERT IGNORE INTO `penilaian_mcu` (', @common_columns, ') ',
        'SELECT ', @common_columns, ' FROM `', @backup_table, '`'
    ),
    'SELECT ''Tidak ada data tabel lama yang perlu disalin'' AS info'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

ALTER TABLE `penilaian_mcu`
    ADD CONSTRAINT `penilaian_mcu_ibfk_1`
        FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `penilaian_mcu_ibfk_2`
        FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`)
        ON DELETE CASCADE ON UPDATE CASCADE;

SELECT
    @backup_table AS backup_tabel_lama,
    COUNT(*) AS jumlah_kolom_baru
FROM information_schema.columns
WHERE table_schema = @db
  AND table_name = 'penilaian_mcu';

DROP TEMPORARY TABLE IF EXISTS `tmp_penilaian_mcu_columns`;
