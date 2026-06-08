-- Samakan pilihan medically fit dan fit with restrictions dengan UI RMMCU.

USE `sik2023_server`;

ALTER TABLE `penilaian_mcu`
  ADD COLUMN `specify` varchar(1000) DEFAULT NULL
  AFTER `fit_with_restrictions`;

ALTER TABLE `penilaian_mcu`
  MODIFY `fit_with_restrictions` varchar(1000) DEFAULT NULL;

UPDATE `penilaian_mcu`
SET `medically_fit` = NULL
WHERE `medically_fit` = '';

UPDATE `penilaian_mcu`
SET
  `specify` = NULLIF(`fit_with_restrictions`,''),
  `fit_with_restrictions` = 'Specify'
WHERE `fit_with_restrictions` IS NOT NULL
  AND `fit_with_restrictions` NOT IN (
    'Medically fit but has following restrictions',
    'Work duties will be restricted',
    'Specify',
    ''
  );

UPDATE `penilaian_mcu`
SET `fit_with_restrictions` = NULL
WHERE `fit_with_restrictions` = '';

ALTER TABLE `penilaian_mcu`
  CHANGE `medically_fit` `medically_fit`
    ENUM('Medically Fit','Presently has minor medical problem','Unfit')
    CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  CHANGE `fit_with_restrictions` `fit_with_restrictions`
    ENUM('Medically fit but has following restrictions','Work duties will be restricted','Specify')
    CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL;
