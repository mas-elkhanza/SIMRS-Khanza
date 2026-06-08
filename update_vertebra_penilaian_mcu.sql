-- Samakan pilihan vertebra pada tabel penilaian_mcu dengan UI RMMCU:
-- Normal, Yes, dan -. Nilai lama No dikonversi menjadi -.

USE `sik2023_server`;

ALTER TABLE `penilaian_mcu`
  MODIFY `vertebra_scoliosis` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_kyphosis` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_lordosis` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_forward_flexion_0_80` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_hyperextensi_0_25` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_lateral_flexion_0_20` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_heel_walking` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_toe_walking` enum('Normal','Yes','No','No') DEFAULT NULL,
  MODIFY `vertebra_squats_x3` enum('Normal','Yes','No','No') DEFAULT NULL;

UPDATE `penilaian_mcu`
SET
  `vertebra_scoliosis` = IF(`vertebra_scoliosis` = 'No', 'No', `vertebra_scoliosis`),
  `vertebra_kyphosis` = IF(`vertebra_kyphosis` = 'No', 'No', `vertebra_kyphosis`),
  `vertebra_lordosis` = IF(`vertebra_lordosis` = 'No', 'No', `vertebra_lordosis`),
  `vertebra_forward_flexion_0_80` = IF(`vertebra_forward_flexion_0_80` = 'No', 'No', `vertebra_forward_flexion_0_80`),
  `vertebra_hyperextensi_0_25` = IF(`vertebra_hyperextensi_0_25` = 'No', 'No', `vertebra_hyperextensi_0_25`),
  `vertebra_lateral_flexion_0_20` = IF(`vertebra_lateral_flexion_0_20` = 'No', 'No', `vertebra_lateral_flexion_0_20`),
  `vertebra_heel_walking` = IF(`vertebra_heel_walking` = 'No', 'No', `vertebra_heel_walking`),
  `vertebra_toe_walking` = IF(`vertebra_toe_walking` = 'No', 'No', `vertebra_toe_walking`),
  `vertebra_squats_x3` = IF(`vertebra_squats_x3` = 'No', 'No', `vertebra_squats_x3`)
WHERE
  `vertebra_scoliosis` = 'No'
  OR `vertebra_kyphosis` = 'No'
  OR `vertebra_lordosis` = 'No'
  OR `vertebra_forward_flexion_0_80` = 'No'
  OR `vertebra_hyperextensi_0_25` = 'No'
  OR `vertebra_lateral_flexion_0_20` = 'No'
  OR `vertebra_heel_walking` = 'No'
  OR `vertebra_toe_walking` = 'No'
  OR `vertebra_squats_x3` = 'No';

ALTER TABLE `penilaian_mcu`
  MODIFY `vertebra_scoliosis` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_kyphosis` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_lordosis` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_forward_flexion_0_80` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_hyperextensi_0_25` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_lateral_flexion_0_20` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_heel_walking` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_toe_walking` enum('Normal','Yes','No') DEFAULT NULL,
  MODIFY `vertebra_squats_x3` enum('Normal','Yes','No') DEFAULT NULL;
