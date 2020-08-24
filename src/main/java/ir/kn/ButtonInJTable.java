package ir.kn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class ButtonInJTable extends JFrame {

    private JPanel topPanel;

    private JTable table;

    private JScrollPane scrollPane;

    private String[] columnNames;

    private String[][] dataValues;

    static JButton button = new JButton();

    public ButtonInJTable() {

        setTitle("Button in JTable Cell");

        setSize(300, 300);

        topPanel = new JPanel();

        topPanel.setLayout(new BorderLayout());

        getContentPane().add(topPanel);

        columnNames = new String[]{"Column 1", "Column 2", "Column 3"};

        dataValues = new String[][]{
                {"1", "2",},
                {"4", "5",},
                {"7", "8",}
        };

        TableModel model = new myTableModel("owntable");

        table = new JTable();
        table.setModel(model);
        table.getColumn("Column 3").setCellRenderer(new ButtonRenderer());
        table.getColumn("Column 3").setCellEditor(new ButtonEditor(new JCheckBox()));

        scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        button.addActionListener(
                event -> JOptionPane.showMessageDialog(null, "Button Clicked in JTable Cell")
        );
    }

    public static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;

        }

    }

     public static class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = (value == null) ? "" : value.toString();
            button.setText(label);

            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }
    }

    public class myTableModel extends DefaultTableModel {

        String dat;

        public myTableModel(String tname) {
            super(dataValues, columnNames);
            dat = tname;
        }

        public boolean isCellEditable(int row, int cols) {

            if (dat.equalsIgnoreCase("owntable")) {
                return cols != 0;
            }
            return true;
        }

    }

    public static void main(String args[]) {
        ButtonInJTable mainFrame = new ButtonInJTable();
        mainFrame.setVisible(true);
    }

}