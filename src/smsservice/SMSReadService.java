/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTextArea;
import org.smslib.AGateway;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageTypes;
import org.smslib.Service;
import org.smslib.modem.ModemGateway.IPProtocols;
import org.smslib.modem.SerialModemGateway;
import smsobj.Status;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author perpustakaan
 */
public class SMSReadService {
    private Status status=null;
    private JTextArea textArea;
    Service service;
    private Connection koneksi;

    public SMSReadService(Connection koneksi,JTextArea textArea){
        this.koneksi = koneksi;
        this.textArea = textArea;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void startService() throws Exception{
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("setting/modem.xml"));
        } catch (IOException ex) {
            Logger.getLogger(SMSReadService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PesanMasukHandler pesanMasuk =new PesanMasukHandler();
        PanggilanMasukHandler panggilanMasuk =new PanggilanMasukHandler();
        GatewayStatusHandler statusGateway =new GatewayStatusHandler();
        
        this.service = new Service();
        SerialModemGateway gateway = new SerialModemGateway (
                prop.getProperty("GATEWAYNAME"), //nama GAteway
                prop.getProperty("PORT"), //Port yang digunakan
                Integer.parseInt(prop.getProperty("BAUDRATE")), //baudrate
                prop.getProperty("MERKHP"), //merk HP
                prop.getProperty("TIPEHP"));//Tipe HP
        
        gateway.setIpProtocol(IPProtocols.BINARY);
        gateway.setProtocol(Protocols.PDU);
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        this.service.setInboundMessageNotification(pesanMasuk);
        this.service.setCallNotification(panggilanMasuk);
        this.service.setGatewayStatusNotification(statusGateway);
        this.service.addGateway(gateway);
        this.service.startService();
        if(status != null ) {
            this.status.setManufacture(gateway.getManufacturer());
            this.status.setModel(gateway.getModel());
            this.status.setSerialNo(gateway.getSerialNo());
            this.status.setSimImsi(gateway.getImsi());
            this.status.setSignal(gateway.getSignalLevel());
            this.status.setBaterai(gateway.getBatteryLevel());
        }
    }

    class PesanMasukHandler implements IInboundMessageNotification{
        public void process(AGateway ag, MessageTypes tipePesan, InboundMessage pesanMasuk) {
            if(tipePesan==MessageTypes.STATUSREPORT) return;
            try {
                String sql = "INSERT INTO sms VALUES(0,?,?,?,?,?,?)";
                PreparedStatement prepare =koneksi.prepareStatement(sql);
                prepare.setString(1, pesanMasuk.getText());
                prepare.setString(2,"+"+pesanMasuk.getMpRefNo());
                prepare.setString(3,pesanMasuk.getPduUserData());
                prepare.setString(4,pesanMasuk.getEncoding().name());
                prepare.setString(5,pesanMasuk.getGatewayId());
                prepare.setDate(6, new java.sql.Date(pesanMasuk.getDate().getTime()));
                prepare.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Pesan Gagal Disimpan");
                System.out.println(ex.getMessage());
            }
            textArea.append("\nPesan Diterima:\n"+pesanMasuk.getSmscNumber() +"\n"+pesanMasuk.getText());
            textArea.append("\n-----------------------------");
            try {
                SMSReadService.this.service.deleteMessage(pesanMasuk);
            } catch (Exception e){
            }
        }

        public void process(String string, MessageTypes mt, InboundMessage im) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class PanggilanMasukHandler implements ICallNotification{
        public void process(AGateway ag, String noHp) {            
        }

        public void process(String string, String string1) {
           
        }
    }

    class GatewayStatusHandler implements IGatewayStatusNotification{
        public void process(AGateway ag, GatewayStatuses statusLama, GatewayStatuses statusBaru) {
        }

        public void process(String string, GatewayStatuses gs, GatewayStatuses gs1) {

        }
    }

}
