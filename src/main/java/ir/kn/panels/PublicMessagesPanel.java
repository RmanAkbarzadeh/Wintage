package ir.kn.panels;

import ir.kn.dblayer.DBLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class PublicMessagesPanel extends SNJPanel implements SetMaterialGf{

	private JTable table;
	
	private String[] columnNames = {"User",
            "Message",
            "Time"};
	
	public PublicMessagesPanel() {
		setSize(880, 600);
                setBackground(new Color(17,33,68));
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 960, 590);
		add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
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
		init();
	}
	
	private void init() {
		try {
			displayMessages(DBLayer.getAllPublicMessages(), table);
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
       jbutton.setBorder(new RoundedBorder(50));
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    }

}
