package ir.kn.panels;
import ir.kn.dblayer.DBLayer;
import ir.kn.panels.LogInPanel;
import ir.kn.panels.SNJPanel;
import ir.kn.panels.SignUpPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.border.Border;

public class SplashPanel extends SNJPanel implements SetMaterialGf{

	private JLabel label;
        private JLabel label2;
        private JLabel textLabel;
        private JLabel textLabel2;
        private JLabel textLabel3;
	
	private String[] columnNames = {"User",
            "Message",
            "Time"};
	
	public SplashPanel() {		
            
		setSize(880, 600);
                setBackground(new Color(17,33,68));
		setLayout(null);
                
                textLabel = new JLabel("See what’s happening in");
                textLabel.setForeground(Color.white);
                textLabel.setBounds(500,-90, 500, 500);
                setBigFont(textLabel);
                add(textLabel);
                 
                textLabel2 = new JLabel("the world right now");
                textLabel2.setForeground(Color.white);
                textLabel2.setBounds(500,-50, 500, 500);              
                setBigFont(textLabel2);
                add(textLabel2);
                
                textLabel3 = new JLabel("Join us today.");
                textLabel3.setForeground(Color.white);
                textLabel3.setBounds(500,320, 200, 15);              
                setBigFont(textLabel3);
                add(textLabel3);
                
                
		label = new JLabel();
                label.setBounds(65, 20, 300, 300); 
                add(label);
                
                label.setIcon(ResizeImage("/home/arman/Downloads/vk.png"));
                
                label2 = new JLabel();
                label2.setBounds(10,-110, 440, 850); 
                add(label2);
                
                label2.setIcon(ResizeImage2("/home/arman/Downloads/rectangle.png"));
                

		
		JButton btnLogIn = new JButton("Log In");
                btnLogIn.setFocusable(false);
                
		btnLogIn.setBackground(new Color(17,33,68));
                btnLogIn.setForeground(Color.white);
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new LogInPanel());
			}
		});
		btnLogIn.setBounds(500, 350, 200, 40);
                setCircleButton(btnLogIn);
		add(btnLogIn);
		
		JButton btnSignUp = new JButton("Sign Up");
                btnSignUp.setFocusable(false);
		btnSignUp.setBackground(new Color(17,33,68));
                btnSignUp.setForeground(Color.white);
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new SignUpPanel());
			}
		});
		btnSignUp.setBounds(720, 350, 200, 40);
                setCircleButton(btnSignUp);
		add(btnSignUp);
                
		
	}
        
     public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
          public ImageIcon ResizeImage2(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label2.getWidth(), label2.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
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
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_NO_START_CONTEXT, 20));
        
    }
}
