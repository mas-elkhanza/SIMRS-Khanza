/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grafikanalisa;

/**
 *
 * @author Via
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import fungsi.koneksiDB;
import java.awt.Font;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author Via
 */
public class grafikresepterbanyak extends JDialog {
    /**
           * Creates a new demo instance.
           *
           * @param title  the frame title.
           */
      public grafikresepterbanyak(String title,String symbol) {
         setTitle(title);
         JPanel chartPanel = createDemoPanel(symbol);
         
         chartPanel.setSize(screen.width,screen.height);
         setContentPane(chartPanel);       
         setModal(true);
         setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
         pack();
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
      private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
        /**
           * Creates a dataset.
           *
           * @return A dataset.
           */

    public static CategoryDataset createDataset1(String symbol) { //data grafik nilai K dan D

          DefaultCategoryDataset result = new DefaultCategoryDataset();
          String series1 = "Obat Resep";
          String series2 = "Nilai Barang(Rp)";
          DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###"); 

            try {
                Statement stat = koneksiDB.condb().createStatement();
                ResultSet rs = stat.executeQuery(
                        "SELECT databarang.nama_brng,sum(detail_pemberian_obat.jml),sum(detail_pemberian_obat.total) from detail_pemberian_obat inner join databarang "+
                        "on detail_pemberian_obat.kode_brng=databarang.kode_brng where "+symbol+" group by databarang.nama_brng order by sum(detail_pemberian_obat.jml) desc limit 10");
                while (rs.next()) {
                    String tksbr=rs.getString(1)+"("+df2.format(rs.getDouble(2))+"; Rp"+df2.format(rs.getDouble(3))+")";
                    double njop=rs.getDouble(2);
                    double jml=rs.getDouble(3);
                    result.addValue(jml, series2,tksbr);
                }
            } catch (SQLException e) {
                System.out.println("Notifikasi : " + e);
            }
            return result;
       }

       /**
          * Creates a dataset.
          *
          * @return A dataset.
          */
        public static CategoryDataset createDataset2(String symbol) {//grafik volume
            DefaultCategoryDataset result = new DefaultCategoryDataset();

             String series1 = "Obat Resep";
             String series2 = "Nilai Barang(Rp)";
             DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");

             try {
                Statement stat = koneksiDB.condb().createStatement();
                ResultSet rs = stat.executeQuery(
                        "SELECT databarang.nama_brng,sum(detail_pemberian_obat.jml),sum(detail_pemberian_obat.total) from detail_pemberian_obat inner join databarang "+
                        "on detail_pemberian_obat.kode_brng=databarang.kode_brng where "+symbol+" group by databarang.nama_brng order by sum(detail_pemberian_obat.jml) desc limit 10");
                while (rs.next()) {
                    String tksbr=rs.getString(1)+"("+df2.format(rs.getDouble(2))+"; Rp"+df2.format(rs.getDouble(3))+")";
                    double njop=rs.getDouble(2);
                    double jml=rs.getDouble(3);

                    result.addValue(njop, series1,tksbr);
                }
            } catch (SQLException e) {
                System.out.println("Notifikasi : " + e);
            }
             return result;
         }

         /**
          * Creates a chart.
          *
          * @return A chart.
          */
         private static JFreeChart createChart(String symbol) {

             CategoryDataset dataset1 = createDataset1(symbol);
             NumberAxis rangeAxis1 = new NumberAxis("Nilai Barang(Rp)");
             rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
             LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
             renderer1.setBaseToolTipGenerator(
                     new StandardCategoryToolTipGenerator());
             CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1,
                     renderer1);
             subplot1.setDomainGridlinesVisible(true);

             CategoryDataset dataset2 = createDataset2(symbol);
             NumberAxis rangeAxis2 = new NumberAxis("Obat Resep");
             rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
             BarRenderer renderer2 = new BarRenderer();
             renderer2.setBaseToolTipGenerator(
                     new StandardCategoryToolTipGenerator());
             CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2,
                     renderer2);
             subplot2.setDomainGridlinesVisible(true);

             CategoryAxis domainAxis = new CategoryAxis("Grafik 10 Barang Resep Ke Pasien Terbanyak");
             CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
             plot.add(subplot1,2 );
             plot.add(subplot2,1 );

             JFreeChart result = new JFreeChart(
                     "",
                     new Font("SansSerif", Font.PLAIN,6 ), plot, true);
             return result;

         }

         /**
          * Creates a panel for the demo (used by SuperDemo.java).
          *
          * @return A panel.
          */

         public static JPanel createDemoPanel(String symbol) {
             JFreeChart chart = createChart(symbol);
             return new ChartPanel(chart);
         }

         /**
          * Starting point for the demonstration application.
          *
          * @param args  ignored.
          */

//        public static void main(String args[]){
//            //        String title = "test Combined Category Plot Demo 1";
//        cocografik demo = new cocografik("aali");
//        JFrame v = new JFrame(title);
//        v.add(demo);
//        v.setBackground(Color.BLUE);
//        v.setSize(new Dimension(1200, 700));
//        v.setDefaultCloseOperation(v.EXIT_ON_CLOSE);
//        v.setVisible(true);
//        }
//           public static void main(String[] args) {
//             String title = "Combined Category Plot Demo ";
//             CombinedCategoryPlotDemo1 demo = new CombinedCategoryPlotDemo1(title);
//             demo.pack();
////             RefineryUtilities.centerFrameOnScreen(demo);
//             demo.setVisible(true);
//
//         }
}

