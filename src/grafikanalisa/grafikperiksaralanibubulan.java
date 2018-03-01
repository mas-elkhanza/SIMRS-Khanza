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
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Font;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class grafikperiksaralanibubulan extends JDialog {
      sekuel Sequel = new sekuel();
      validasi Valid = new validasi();
      public grafikperiksaralanibubulan(String title,String symbol) {
          setTitle(title);
         JPanel chartPanel = createDemoPanel(symbol);
         
         chartPanel.setSize(screen.width,screen.height);
         setContentPane(chartPanel);       
         setModal(true);
         setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
         pack();
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
      
    public static CategoryDataset createDataset1(String symbol) { //data grafik nilai K dan D

          DefaultCategoryDataset result = new DefaultCategoryDataset();
          String series1 = "Periksa Perbulan";
          
            try {
                Statement stat = koneksiDB.condb().createStatement();
                ResultSet rs = stat.executeQuery(symbol);
                while (rs.next()) {
                    String tksbr=rs.getString(1)+"("+rs.getString(2)+")";
                    double field1=rs.getDouble(2);
                    double field2=rs.getDouble(2);

                    //result.addValue(field1, series1,tksbr);
                    result.addValue(field2, series1,tksbr);
                }
            } catch (SQLException e) {
                System.out.println("Notifikasi : " + e);
            }
            return result;
       }

        public static CategoryDataset createDataset2(String symbol) {//grafik volume
            DefaultCategoryDataset result = new DefaultCategoryDataset();
            String series1 = "Periksa Perbulan";             

            try {
                Statement stat = koneksiDB.condb().createStatement();
                ResultSet rs = stat.executeQuery(symbol);
                while (rs.next()) {
                    String tksbr=rs.getString(1)+"("+rs.getString(2)+")";
                    double field1=rs.getDouble(2);
                    double field2=rs.getDouble(2);

                    result.addValue(field1, series1,tksbr);
                }
            } catch (SQLException e) {
                System.out.println("Notifikasi : " + e);
            }
             return result;
         }


         private static JFreeChart createChart(String symbol) {

             CategoryDataset dataset1 = createDataset1(symbol);
             NumberAxis rangeAxis1 = new NumberAxis("Jumlah");
             rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
             LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
             renderer1.setBaseToolTipGenerator(
                     new StandardCategoryToolTipGenerator());
             CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1,
                     renderer1);
             subplot1.setDomainGridlinesVisible(true);

             CategoryDataset dataset2 = createDataset2(symbol);
             NumberAxis rangeAxis2 = new NumberAxis("Jumlah");
             rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
             BarRenderer renderer2 = new BarRenderer();
             renderer2.setBaseToolTipGenerator(
                     new StandardCategoryToolTipGenerator());
             CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2,
                     renderer2);
             subplot2.setDomainGridlinesVisible(true);

             CategoryAxis domainAxis = new CategoryAxis("Periksa Perbulan");
             CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
             plot.add(subplot1,2 );
             plot.add(subplot2,1 );

             JFreeChart result = new JFreeChart("",new Font("SansSerif", Font.PLAIN,6 ), plot, true);
             return result;

         }

         public static JPanel createDemoPanel(String symbol) {
             JFreeChart chart = createChart(symbol);
             return new ChartPanel(chart);
         }
}

