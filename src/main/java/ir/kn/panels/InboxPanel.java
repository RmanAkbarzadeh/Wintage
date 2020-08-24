package ir.kn.panels;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.Message;
import ir.kn.utilities.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class InboxPanel extends SNJPanel implements SetMaterialGf{
    private JTable table;
    private String[] columnNames = {"User",
            "Message",
            "Time",
            "RePost"};

    public InboxPanel() {
        setSize(860, 600);
        setLayout(null);
        setBackground(new Color(17,33,68));
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 41, 840, 548);
        panel.setBackground(new Color(29, 161, 242));
        add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 840, 548);
        panel.add(scrollPane);

        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String s : columnNames) {
            model.addColumn(s);
        }
        table = new JTable(model);
        scrollPane.setViewportView(table);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(29, 161, 242));
        btnRefresh.setForeground(Color.white);
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                init();
            }
        });
        btnRefresh.setBounds(726, 5, 170, 30);
        setCircleButton(btnRefresh);
        add(btnRefresh);
    }

    public void init() {
        try {
            displayMessages(DBLayer.getInboxMessages(getRootFrame().getSession().getUser().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMessages(List<Message> messages) {
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.setBackground(new Color(20, 117, 181));
        table.setGridColor(new Color(20, 117, 181));
        table.setForeground(Color.white);
        table.setRowHeight(40);
        table.setSelectionBackground(new Color(17,33,68));
        table.setSelectionForeground(Color.WHITE);
        table.setIntercellSpacing(new Dimension(15,10));
        table.setFont(new Font("Serif", Font.BOLD, 13));
        setLocation(new Point(700,300));
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(17,33,68));
        tableHeader.setForeground(Color.white);
        tableHeader.setPreferredSize(new Dimension(30,30));
        tableHeader.setFont(new Font("Serif", Font.BOLD, 13));
        

        for (Message m : messages) {
            model.addRow(new Object[]{
                    m.getSender().getName(),
                    m.getText(),
                    m.getSendingTime(),
                    "RePost"});
        }

        Action rePost = new AbstractAction() {

            public void actionPerformed(ActionEvent actionEvent) {

                try {

                    int rowIndex = ((JTable) actionEvent.getSource()).getSelectedRow();

                    System.out.println("Row number = " + rowIndex);
                  //  int messageId = messages.get(rowIndex).getId();
                    String messageText = messages.get(rowIndex).getText();
                    
                    DBLayer.rePost(messageText, getRootFrame().getSession().getUser().getId());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, rePost, 3, false, true);
       
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        table.getColumnModel().getColumn(3).setPreferredWidth(40);
    }
    private static class RoundedBorder implements Border {

           private int radius;


           RoundedBorder(int radius) {
               this.radius = radius;
            }


           public Insets getBorderInsets(Component c) {
                return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
            }


           public boolean isBorderOpaque() {
                return true;
            }


            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width-1, height-1, radius, radius);
             }
      }

    @Override
    public void setBigFont(JLabel jlabel) {
                Font labelFont = jlabel.getFont();
                String labelText = jlabel.getText();
                int stringWidth = jlabel.getFontMetrics(labelFont).stringWidth(labelText);
                int componentWidth = jlabel.getWidth();
                double widthRatio = (double)componentWidth / (double)stringWidth;
                int newFontSize = (int)(labelFont.getSize() * widthRatio);
                int componentHeight = jlabel.getHeight();
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                jlabel.setFont(new Font(labelFont.getName(), Font.LAYOUT_NO_START_CONTEXT, fontSizeToUse));
    }

    @Override
    public void setCircleButton(JButton jbutton) {
       jbutton.setBorder(new RoundedBorder(50));
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    }

}
