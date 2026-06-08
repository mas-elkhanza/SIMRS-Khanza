-- Bersihkan kolom penilaian_mcu yang sudah tidak digunakan oleh RMMCU.
-- Seluruh isi tabel dibackup sebelum kolom legacy dihapus.
-- Aman dijalankan ulang: hanya kolom di luar daftar aktif yang akan dihapus.

USE `sik2023_server`;

SET @db = DATABASE();
SET SESSION group_concat_max_len = 1000000;

DROP TEMPORARY TABLE IF EXISTS `tmp_penilaian_mcu_keep_columns`;
CREATE TEMPORARY TABLE `tmp_penilaian_mcu_keep_columns` (
    `column_name` varchar(128) NOT NULL,
    PRIMARY KEY (`column_name`)
);

DROP PROCEDURE IF EXISTS `fill_penilaian_mcu_keep_columns`;
DELIMITER $$
CREATE PROCEDURE `fill_penilaian_mcu_keep_columns`(IN p_columns longtext)
BEGIN
    DECLARE v_column varchar(128);
    WHILE p_columns IS NOT NULL AND p_columns <> '' DO
        SET v_column = TRIM(SUBSTRING_INDEX(p_columns, ',', 1));
        INSERT IGNORE INTO `tmp_penilaian_mcu_keep_columns` (`column_name`) VALUES (v_column);
        IF LOCATE(',', p_columns) = 0 THEN
            SET p_columns = '';
        ELSE
            SET p_columns = SUBSTRING(p_columns, LOCATE(',', p_columns) + 1);
        END IF;
    END WHILE;
END$$
DELIMITER ;

CALL `fill_penilaian_mcu_keep_columns`(CONCAT(
    'no_rawat,tanggal,year,kd_dokter,kd_petugas,note1,nama_pasien,surname,mcu_group,dass_21,phy_exam,conc_lab,conc_radiologi,conc_ecg,conc_spirometry,conc_audiometry,kesimpulan1,no_rkm_medis,tmp_lahir,tgl_lahir,jk,no_tlp,suku_bangsa,stts_nikah,doe,yoe,job_title,activities,hobby,other_job,posisi_kerja,',
    'job_involves_driving_or_operating_mobile_equipment,job_involves_working_at_heights,job_involves_clerical_office_based_or_administrative,job_involves_requires_colour_vision,job_involves_potential_dust_exposure,job_involves_catering_staff_including_food_handlers,job_involves_exposing_to_other_potential_dangerous,',
    'med_hist_head_injury_or_contussion,med_hist_fainting_blackouts_epilepsy,med_hist_visual_changes,med_hist_hearing_loss,med_hist_nose_sinus_throat_trouble_more_4_weeks,med_hist_gynaecological_problems,med_hist_chronic_skin_problem,med_hist_chronic_diarrhea,med_hist_anorexia_more_4_weeks,med_hist_gastritis,med_hist_jaundice_hepatitis,med_hist_chronic_cough_more_4_weeks,med_hist_haemorhoid,med_hist_chronic_abdominal_pain,med_hist_diabetes,med_hist_asthma,med_hist_allergies,med_hist_tuberculosis_bronchitis,med_hist_psychiatric_disorder,med_hist_sexual_transmitted_diseases,med_hist_unusual_change_of_weight_more_5kg_per_month,med_hist_hypertension,med_hist_chest_pain_heart_disease,med_hist_malaria_tropical_disease,med_hist_surgery_operation,med_hist_back_pain_more_4_weeks,med_hist_thypoid_fever,med_hist_swollen_or_painful_joints,med_hist_kidney_problem_urinary_stones,med_hist_other_chronical_diseases,',
    'family_history_father,family_history_mother,family_history_siblings,family_history_other,cigarettes_perday,alcohol_gr_week,prescribed_medication,prescribed_medication_2,any_allergies,hb,wbc,esr,bl_group,gamaa_gt,sgot,sgpt,urea,creatinin,glucose,random_glucose,total_cholestrol,protein,blood,bilirubin,malaria,tpha,mantoux_test,leukosit,lab_others,ova,culture,cysta,parasites1,pnemunosicosis,pnemunosicosis2,ILO_clasification,ILO_clasification2,oth_abnormal,tb1,tb2,page3_comment,',
    'td,nadi,rr,tb,bb,bmi,kasifikasi_bmi,laborat,radiologi,ekg,spirometri_vc_1,spirometri_vc_2,spirometri_vc_3,spirometri_vc_4,spirometri_fvc_1,spirometri_fvc_2,spirometri_fvc_3,spirometri_fvc_4,spirometri_fev_1_1,spirometri_fev_1_2,spirometri_fev_1_3,spirometri_fev_1_4,spirometri_fev_1_fvc_1,spirometri_fev_1_fvc_2,spirometri_fev_1_fvc_3,spirometri_fev_1_fvc_4,',
    'audiometri_tinitus_never,audiometri_tinitus_previously,audiometri_tinitus_rarely,audiometri_tinitus_often,audiometri_tinitus_always,audiometri_ear_protection_worn_never,audiometri_ear_protection_worn_previously,audiometri_ear_protection_worn_rarely,audiometri_ear_protection_worn_often,audiometri_ear_protection_worn_always,type_of_hearing,audiometri_left_ear_500_AB,audiometri_left_ear_1000_AB,audiometri_left_ear_1500_AB,audiometri_left_ear_2000_AB,audiometri_left_ear_3000_AB,audiometri_left_ear_4000_AB,audiometri_left_ear_5000_AB,audiometri_left_ear_6000_AB,audiometri_left_ear_500_AC,audiometri_left_ear_1000_AC,audiometri_left_ear_1500_AC,audiometri_left_ear_2000_AC,audiometri_left_ear_3000_AC,audiometri_left_ear_4000_AC,audiometri_left_ear_5000_AC,audiometri_left_ear_6000_AC,audiometri_right_ear_500_ab,audiometri_right_ear_1000_ab,audiometri_right_ear_1500_ab,audiometri_right_ear_2000_ab,audiometri_right_ear_3000_ab,audiometri_right_ear_4000_ab,audiometri_right_ear_5000_ab,audiometri_right_ear_6000_ab,audiometri_right_ear_500_ac,audiometri_right_ear_1000_ac,audiometri_right_ear_1500_ac,audiometri_right_ear_2000_ac,audiometri_right_ear_3000_ac,audiometri_right_ear_4000_ac,audiometri_right_ear_5000_ac,audiometri_right_ear_6000_ac,',
    'eye_unaided_distant_r,eye_unaided_distant_l,eye_glasses_distant_r,eye_glasses_distant_l,eye_unaided_near_r,eye_unaided_near_l,eye_glasses_near_r,eye_glasses_near_l,eye_night_vision_1,eye_night_vision_2,eye_brake_test_1,eye_brake_test_2,eye_color_blindless,visual_fields_left,visual_fields_right,fundi,imunisasi_bcg,imunisasi_dpt,imunisasi_polio,imunisasi_morbili,imunisasi_thyphoid,imunisasi_hep_a,imunisasi_hep_b,imunisasi_tetanus,imunisasi_others,vertebra_scoliosis,vertebra_kyphosis,vertebra_lordosis,vertebra_forward_flexion_0_80,vertebra_hyperextensi_0_25,vertebra_lateral_flexion_0_20,vertebra_heel_walking,vertebra_toe_walking,vertebra_squats_x3,exam_ent_comments,exam_cardio_vascular_system_comments,exam_respiratory_system_comments,exam_abdomen_comments,exam_genito_urinary_system_comments,exam_central_peripheral_nervous_system_comments,exam_skin_comments,exam_lymph_nodes_comments,exam_dental_comments,conclusion_requires_spectacles,conclusion_colour_blindness,conclusion_respiratory_problem,conclusion_impaired_hearing,conclusion_vertigo,blood_group,medically_fit,fit_with_restrictions,specify,unfit_comment_1'
));

DROP PROCEDURE IF EXISTS `fill_penilaian_mcu_keep_columns`;

-- Hentikan proses jika daftar aktif tidak lengkap.
SET @keep_count = (SELECT COUNT(*) FROM `tmp_penilaian_mcu_keep_columns`);
DROP PROCEDURE IF EXISTS `assert_penilaian_mcu_keep_columns`;
DELIMITER $$
CREATE PROCEDURE `assert_penilaian_mcu_keep_columns`()
BEGIN
    IF @keep_count <> 230 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Daftar kolom aktif RMMCU tidak valid';
    END IF;
END$$
DELIMITER ;
CALL `assert_penilaian_mcu_keep_columns`();
DROP PROCEDURE IF EXISTS `assert_penilaian_mcu_keep_columns`;

SELECT c.column_name, c.column_type
FROM information_schema.columns c
LEFT JOIN `tmp_penilaian_mcu_keep_columns` k ON k.column_name = c.column_name
WHERE c.table_schema = @db
  AND c.table_name = 'penilaian_mcu'
  AND k.column_name IS NULL
ORDER BY c.ordinal_position;

SET @drop_count = (
    SELECT COUNT(*)
    FROM information_schema.columns c
    LEFT JOIN `tmp_penilaian_mcu_keep_columns` k ON k.column_name = c.column_name
    WHERE c.table_schema = @db
      AND c.table_name = 'penilaian_mcu'
      AND k.column_name IS NULL
);

SET @backup_table = CONCAT('penilaian_mcu_backup_cleanup_', DATE_FORMAT(NOW(), '%Y%m%d_%H%i%s'));
SET @sql = CONCAT('CREATE TABLE `', @backup_table, '` LIKE `penilaian_mcu`');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = CONCAT('INSERT INTO `', @backup_table, '` SELECT * FROM `penilaian_mcu`');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT GROUP_CONCAT(
    DISTINCT CONCAT('DROP FOREIGN KEY `', kcu.constraint_name, '`')
    SEPARATOR ', '
)
INTO @drop_fk_sql
FROM information_schema.key_column_usage kcu
LEFT JOIN `tmp_penilaian_mcu_keep_columns` k ON k.column_name = kcu.column_name
WHERE kcu.table_schema = @db
  AND kcu.table_name = 'penilaian_mcu'
  AND kcu.referenced_table_name IS NOT NULL
  AND k.column_name IS NULL;

SET @sql = IF(
    @drop_fk_sql IS NULL OR @drop_fk_sql = '',
    'SELECT ''Tidak ada foreign key legacy'' AS info',
    CONCAT('ALTER TABLE `penilaian_mcu` ', @drop_fk_sql)
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT GROUP_CONCAT(
    CONCAT('DROP COLUMN `', c.column_name, '`')
    ORDER BY c.ordinal_position
    SEPARATOR ', '
)
INTO @drop_column_sql
FROM information_schema.columns c
LEFT JOIN `tmp_penilaian_mcu_keep_columns` k ON k.column_name = c.column_name
WHERE c.table_schema = @db
  AND c.table_name = 'penilaian_mcu'
  AND k.column_name IS NULL;

SET @sql = IF(
    @drop_column_sql IS NULL OR @drop_column_sql = '',
    'SELECT ''Tidak ada kolom legacy yang perlu dihapus'' AS info',
    CONCAT('ALTER TABLE `penilaian_mcu` ', @drop_column_sql)
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT
    @backup_table AS backup_tabel,
    @drop_count AS jumlah_kolom_dihapus,
    COUNT(*) AS jumlah_kolom_tersisa
FROM information_schema.columns
WHERE table_schema = @db
  AND table_name = 'penilaian_mcu';

DROP TEMPORARY TABLE IF EXISTS `tmp_penilaian_mcu_keep_columns`;
