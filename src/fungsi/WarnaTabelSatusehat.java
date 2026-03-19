package fungsi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTabelSatusehat extends DefaultTableCellRenderer {
    private static final Color WARNA_BIRU_WINDOWS = new Color(51, 51, 51);
    private Set<String> mappedKodeBarang = new HashSet<>();
    private boolean cacheLoaded = false;
    private Connection koneksi;
    
    private static WarnaTabelSatusehat instance;
    
    public WarnaTabelSatusehat(Connection koneksi) {
        this.koneksi = koneksi;
        instance = this;
    }
    
    public static WarnaTabelSatusehat getInstance() {
        return instance;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (!cacheLoaded) {
            loadMappingCache();
        }
        
        if (row % 2 == 1){
            component.setBackground(new Color(229,248,255));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        
        if (isSelected) {
            component.setForeground(WARNA_BIRU_WINDOWS);
            component.setFont(component.getFont().deriveFont(Font.BOLD));
        } else {
            if (column == 1 && value != null && !value.toString().trim().isEmpty()) {
                String kodeBarang = value.toString().trim();
                
                if (mappedKodeBarang.contains(kodeBarang)) {
                    component.setForeground(Color.RED);
                    component.setFont(component.getFont().deriveFont(Font.BOLD));
                } else {
                    component.setForeground(Color.BLACK);
                }
            } else {
                component.setForeground(Color.BLACK);
            }
            
            if (!isSelected) {
                component.setFont(component.getFont().deriveFont(Font.PLAIN));
            }
        }
        
        return component;
    }
    
    private void loadMappingCache() {
        if (koneksi == null) {
            return;
        }
        
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "SELECT kode_brng FROM satu_sehat_mapping_obat"
            );
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String kode = rs.getString("kode_brng");
                mappedKodeBarang.add(kode);
            }
            
            rs.close();
            ps.close();
            cacheLoaded = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refreshCache() {
        mappedKodeBarang.clear();
        cacheLoaded = false;
        loadMappingCache();
    }
    
    public static void refreshCacheGlobal() {
        if (instance != null) {
            instance.refreshCache();
        }
    }
    
    public void clearCache() {
        mappedKodeBarang.clear();
        cacheLoaded = false;
    }
}