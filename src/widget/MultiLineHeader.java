package widget;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class MultiLineHeader implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(table.getTableHeader().getBackground());
        panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));

        String text = (value != null ? value.toString() : "");

        int columnWidth = table.getColumnModel().getColumn(column).getWidth();
        JLabel measureLabel = new JLabel();
        measureLabel.setFont(table.getTableHeader().getFont());
        FontMetrics fm = measureLabel.getFontMetrics(measureLabel.getFont());

        int textWidth = fm.stringWidth(text);
        int availableWidth = columnWidth - 10;

        JLabel label;

        if (textWidth > availableWidth && availableWidth > 0) {
            // Coba pecah di spasi terakhir
            int breakIndex = text.lastIndexOf(' ');
            if (breakIndex > 0) {
                String part1 = text.substring(0, breakIndex).trim();
                String part2 = text.substring(breakIndex + 1).trim();
                String htmlText = "<html><center>" + part1 + "<br>" + part2 + "</center></html>";
                label = new JLabel(htmlText);
            } else {
                // Tidak ada spasi, pakai teks utuh
                label = new JLabel(text);
            }
        } else {
            label = new JLabel(text);
        }

        label.setFont(table.getTableHeader().getFont());
        label.setForeground(table.getTableHeader().getForeground());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(label);
        return panel;
    }
}
