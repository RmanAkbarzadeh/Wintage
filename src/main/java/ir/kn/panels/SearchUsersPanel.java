package ir.kn.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.Message;
import ir.kn.utilities.ButtonColumn;
import ir.kn.entity.Subscription;
import ir.kn.entity.User;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class SearchUsersPanel extends SNJPanel implements SetMaterialGf{
    private JTextField name;
    private JLabel message;
    private List<User> users = new ArrayList<>();

    private String[] columnNames = {"Name",
            "Bio",
            "Links",
            "Phone",
            "Email",
            "Subscribe",
            "See"};
    private JTable table;

    /**
     * Create the panel.41
     */
    public SearchUsersPanel() {
        setLayout(null);
        setSize(860, 600);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 860, 36);
        add(panel);
        panel.setLayout(null);

        JLabel lblSearchUser = new JLabel("Search User:");
        lblSearchUser.setBounds(10, 11, 95, 14);
        panel.add(lblSearchUser);

        name = new JTextField();
        name.setBounds(115, 8, 451, 20);
        panel.add(name);
        name.setColumns(10);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(17,33,68));
        btnSearch.setForeground(Color.white);
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                searchUsers();
            }
        });
        btnSearch.setBounds(576, 2, 160, 30);
        setCircleButton(btnSearch);
        panel.add(btnSearch);

        message = new JLabel("");
        message.setBounds(713, 11, 137, 14);
        panel.add(message);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 36, 850, 553);
        add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 850, 553);
        panel_1.add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();
        for (String s : columnNames) {
            model.addColumn(s);
        }

        table = new JTable(model);
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
        scrollPane.setViewportView(table);
        //table.setEnabled(false);
    }

    protected void searchUsers() {
        try {
            users = DBLayer.searchUsers(name.getText());
            System.out.println("Total search results = " + users.size());
            displayUsersList(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayUsersList(List<User> users) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
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
        model.setRowCount(0);
        

        for (User u : users) {

            String btnLbl;

            boolean alreadySubscribed = false;

            for (Subscription s : getRootFrame().getSession().getUser().getSubscriptions()) {
                if (s.getSubscribedId() == u.getId() || u.getId()==getRootFrame().getSession().getUser().getId()) {
                    alreadySubscribed = true;
                    break;
                }
            }

            if (!alreadySubscribed) {
                btnLbl = "Subscribe";
            } else {
                btnLbl = "-";
            }

            model.addRow(new Object[]{
                    u.getName(),
                    u.getBio(),
                    u.getLinks(),
                    u.getPhone(),
                    u.getEmail(),
                    btnLbl,
                    "See"});
        }

        Action subscribe = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int rowIndex = Integer.parseInt(e.getActionCommand());

                    System.out.println("Row number = " + rowIndex);
                    int subscribed_id = users.get(rowIndex).getId();
                    boolean alreadySubscribed = false;

                    for (Subscription s : getRootFrame().getSession().getUser().getSubscriptions()) {
                        System.out.println("Bekhooon" + getRootFrame().getSession().getUser().getId());
                        if (s.getSubscribedId() == subscribed_id || getRootFrame().getSession().getUser().getId()== s.getSubscriberId()) {
                            alreadySubscribed = true;
                            break;
                        }
                    }

                    if (((JTable) e.getSource()).getSelectedColumn() == 6){

                        showFollow(subscribed_id);
                    }

                    else if (!alreadySubscribed) {
                        DBLayer.subscribe(getRootFrame().getSession().getUser().getId(), subscribed_id);
                        message.setText("Successfully subscribed");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    message.setText(e1.getMessage());
                }
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, subscribe, 5, false, false);
        ButtonColumn tmp = new ButtonColumn(table, subscribe, 6, false, false);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
        tmp.setMnemonic(KeyEvent.VK_D);

    }

    private void showFollow(int userId) {

        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnNames = {"Name",
                "Message",
                "Date"};

        for (String s : columnNames) {
            model.addColumn(s);
        }

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

        followingTableData(model, userId);


        JFrame parent = new JFrame("user info");
        parent.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JTable myTable = new JTable(model);

        myTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        myTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        myTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        panel.add(new JScrollPane(myTable));

        parent.add(panel);
        parent.pack();
        parent.setVisible(true);

    }

    private void followingTableData(DefaultTableModel model, int userId) {

        try {

            List<Message> messages = new ArrayList<>(DBLayer.findUserPosts(userId));
            model.setRowCount(0);

            for (Message m : messages) {
                model.addRow(new Object[]{
                        m.getSender().getName(),
                        m.getText(),
                        m.getSendingTime()});
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    }

}