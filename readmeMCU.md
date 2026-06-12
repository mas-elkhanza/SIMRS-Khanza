# Struktur Database RMMCU

Dokumen ini menjelaskan kontrak database untuk
`src/rekammedis/RMMCU.java`.

## Sumber Kebenaran

- Tabel utama: `penilaian_mcu`
- Database lokal: `sik2023_server`
- Daftar kolom aktif: konstanta `PENILAIAN_MCU_COLUMNS` di `RMMCU.java`
- Jumlah kolom aktif saat ini: **231 kolom unik**

Urutan kolom pada `PENILAIAN_MCU_COLUMNS` tidak boleh diubah sembarangan.
Urutan tersebut dipakai langsung oleh proses simpan dan edit RMMCU.

## Relasi Utama

Struktur minimum relasi tabel:

```sql
ALTER TABLE `penilaian_mcu`
  ADD PRIMARY KEY (`no_rawat`),
  ADD CONSTRAINT `penilaian_mcu_ibfk_1`
    FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `penilaian_mcu_ibfk_2`
    FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`)
    ON DELETE CASCADE ON UPDATE CASCADE;
```

`kd_petugas` berisi NIP petugas penginput. Identitas pasien, perusahaan,
dan dokter kunjungan awal diambil melalui `reg_periksa`.

## Kelompok Kolom

| Kelompok | Kolom utama |
|---|---|
| Identitas | `no_rawat`, `tanggal`, `year`, `kd_dokter`, `kd_petugas`, `no_rkm_medis`, `nama_pasien`, `surname` |
| Pekerjaan | `mcu_group`, `posisi_kerja`, `job_involves_*` |
| Riwayat medis | `med_hist_*`, `family_history_*`, obat dan alergi |
| Laboratorium | `conc_lab`, `laborat`, `hb` sampai `parasites1` |
| Radiologi dan ECG | `conc_radiologi`, `radiologi`, `conc_ecg`, `ekg` |
| Spirometri | `conc_spirometry`, `spirometri_*` |
| Audiometri | `conc_audiometry`, `audiometri_*`, `type_of_hearing` |
| Mata | `eye_*`, `visual_fields_*`, `fundi` |
| Imunisasi | `imunisasi_*` |
| Vertebra | `vertebra_*` |
| Pemeriksaan sistem | `exam_*` |
| Kesimpulan | `conclusion_*`, `blood_group`, `medically_fit`, `fit_with_restrictions`, `specify`, `unfit_comment_1` |

## Nilai UI Yang Wajib Sesuai

| Kolom | Nilai database |
|---|---|
| `conc_ecg` | `Normal`, `Abnormal` |
| `ekg` | `Normal`, atau keterangan dari `ecg_abnormal` ketika abnormal |
| `vertebra_*` | `Normal`, `Yes`, `No` |
| `eye_color_blindless` | `Color Blind`, `Normal`, `Partial Color Blind` |
| `eye_glasses_*`, `eye_unaided_near_*`, `eye_night_vision_*`, `eye_brake_test_*` | `No`, `Yes` |
| `imunisasi_*` | `-`, `No`, `Yes` |
| `conclusion_*` | `No`, `Yes` |
| `blood_group` | `A+`, `A-`, `B+`, `B-`, `AB+`, `AB-`, `O+`, `O-` |
| `medically_fit` | `Medically Fit`, `Presently has minor medical problem`, `Unfit` |
| `fit_with_restrictions` | `Medically fit but has following restrictions`, `Work duties will be restricted`, `Specify` |
| `specify` | Teks bebas, hanya dipakai ketika `fit_with_restrictions = 'Specify'` |

Kolom teks bebas seperti hasil laboratorium, komentar pemeriksaan,
kesimpulan, saran, dan detail abnormal sebaiknya menggunakan `TEXT` atau
`VARCHAR` yang cukup panjang, bukan `ENUM`.

Tipe kolom pada migrasi mengikuti komponen UI RMMCU:

- `ComboBox` menggunakan `ENUM` sesuai pilihan pada form.
- `CheckBox` menggunakan `ENUM('No','Yes')`.
- `TextArea` dan `TextBox` berisi narasi bebas menggunakan `TEXT`.
- `TextBox` untuk identitas, hasil ukur, hasil laboratorium, spirometri,
  dan audiometri menggunakan `VARCHAR` dengan panjang yang sesuai.
- Tanggal dan identitas tertentu memiliki tipe atau panjang khusus.

## Migrasi UI Baru

Jalankan setelah backup database:

```text
update_ui_baru_rmmcu.sql
```

Skrip tersebut mengganti nama tabel `penilaian_mcu` lama menjadi tabel
backup bertimestamp, membuat ulang tabel dengan seluruh 231 kolom aktif
RMMCU, lalu menyalin kembali data pada kolom lama yang namanya masih sama.
Kolom legacy tetap tersedia di tabel backup dan tidak dimasukkan ke tabel
aktif. Skrip juga dapat dijalankan ketika tabel dasar `penilaian_mcu` belum
tersedia.

Jalankan seluruh isi `update_ui_baru_rmmcu.sql` sekaligus. Skrip tidak
menggunakan `DELIMITER` atau stored procedure sehingga dapat langsung
dieksekusi melalui phpMyAdmin, HeidiSQL, atau MySQL client.

## Pemeriksaan Struktur

```sql
SELECT COUNT(*) AS jumlah_kolom
FROM information_schema.columns
WHERE table_schema = DATABASE()
  AND table_name = 'penilaian_mcu';

SHOW CREATE TABLE `penilaian_mcu`;

SELECT `no_rawat`, `conc_ecg`, `ekg`,
       `medically_fit`, `fit_with_restrictions`, `specify`
FROM `penilaian_mcu`
ORDER BY `tanggal` DESC
LIMIT 20;
```

Struktur lengkap yang digunakan RMMCU saat ini berjumlah **231 kolom**.
Bila jumlah atau nama kolom berubah di `RMMCU.java`, perbarui README dan
migrasi database sebelum menjalankan aplikasi.
