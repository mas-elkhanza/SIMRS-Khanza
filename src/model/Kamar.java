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
public class Kamar {
    private String kd_kamar;
    private Bangsal kd_bangsal;
    private double trf_kamar;
    private String status;
    private String kelas;
    private String statusdata;
}
