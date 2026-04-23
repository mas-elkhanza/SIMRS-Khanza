/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package freehand;

/**
 *
 * @author salimmulyana
 */
import java.awt.Image;
import java.io.File;
import javax.swing.*;

public class GambarPanel extends javax.swing.JFrame {
    private JLabel gambarLabel;

    public GambarPanel() {
        initComponents();
    }

    private void initComponents() {
        JPanel panelGambar = new JPanel();
        JButton btnBrowse = new JButton("Browse");
        gambarLabel = new JLabel();

        gambarLabel.setPreferredSize(new java.awt.Dimension(400, 300));
        gambarLabel.setHorizontalAlignment(JLabel.CENTER);

        panelGambar.add(gambarLabel);

        btnBrowse.addActionListener(evt -> browseGambar());

        setLayout(new java.awt.BorderLayout());
        add(panelGambar, java.awt.BorderLayout.CENTER);
        add(btnBrowse, java.awt.BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pilih dan Tampilkan Gambar");
    }

    private void browseGambar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Gambar (JPG, PNG, BMP)", "jpg", "jpeg", "png", "bmp"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());

            SwingUtilities.invokeLater(() -> {
                int width = gambarLabel.getWidth();
                int height = gambarLabel.getHeight();

                if (width > 0 && height > 0) {
                    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    gambarLabel.setIcon(new ImageIcon(image));
                } else {
                    System.err.println("JLabel width/height is 0. Adjust your layout.");
                }
            });
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GambarPanel().setVisible(true));
    }
}
