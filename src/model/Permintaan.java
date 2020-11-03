/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

/**
 *
 * @author RSUI HA
 */
@Data
public class Permintaan {
    private String jumlah;
    private String kodebarang;
    private String namabarang;
    private String satuan;
    private String jenis;
    private String kategori;
    private String golongan;
    private String keterangan;
}
