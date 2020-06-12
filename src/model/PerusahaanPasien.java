/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class PerusahaanPasien {
    private String kode_perusahaan;
    private String nama_perusahaan;
    private String alamat;
    private String kota;
    private String no_telp;

}
