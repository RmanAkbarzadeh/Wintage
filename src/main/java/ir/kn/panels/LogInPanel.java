package ir.kn.panels;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.Session;
import ir.kn.entity.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public class LogInPanel extends SNJPanel implements SetMaterialGf{

	private JTextField email;
	private JTextField password;
	private JLabel message;
	
	public LogInPanel() {
		setBackground(new Color(17,33,68));
		setSize(880, 600);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(230, 100, 499, 317);
                panel.setBackground(new Color(29, 161, 242));
		panel.setLayout(null);
		add(panel);
		
		JLabel EmailLb = new JLabel("Email:");
		EmailLb.setBounds(77, 140, 91, 17);
                EmailLb.setForeground(Color.white);
                setBigFont(EmailLb);
		panel.add(EmailLb);
		
		JLabel PasswordLb = new JLabel("Password:");
		PasswordLb.setBounds(77, 153, 100, 70);
                PasswordLb.setForeground(Color.white);
                setBigFont(PasswordLb);
		panel.add(PasswordLb);
                
                JLabel ShowLb = new JLabel("Please enter your email and password :");
                ShowLb.setForeground(Color.white);
		ShowLb.setBounds(65, 60, 450, 40);
                setBigFont(ShowLb);
		panel.add(ShowLb);
		
		email = new JTextField();
		email.setText("");
		email.setColumns(10);
		email.setBounds(178, 140, 224, 20);
		panel.add(email);
		
		password = new JPasswordField();
		password.setText("");
		password.setColumns(10);
		password.setBounds(178, 177, 224, 20);
		panel.add(password);
		
		JButton LogInButton = new JButton("Log In");
		LogInButton.setBackground(new Color(29, 161, 242));
                LogInButton.setForeground(Color.GREEN);
		LogInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
                            try {
                                login();
                            } catch (IOException ex) {
                                Logger.getLogger(LogInPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		LogInButton.setBounds(90, 260, 160, 30);
                setCircleButton(LogInButton);
		panel.add(LogInButton);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.setBackground(new Color(29, 161, 242));
                CancelButton.setForeground(Color.RED);
		CancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new SplashPanel());
			}
		});
		CancelButton.setBounds(270, 260, 160, 30);
                setCircleButton(CancelButton);
		panel.add(CancelButton);
		
		JButton SignUpButton = new JButton("SignUp");
		SignUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new SignUpPanel());
			}
		});
		SignUpButton.setBounds(350, 11, 110, 23);
                SignUpButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		panel.add(SignUpButton);
		
		message = new JLabel("");
		message.setBounds(130, 220, 280, 20);
                message.setForeground(Color.white);
                setBigFont(message);
		panel.add(message);
		

	}
	protected void login() throws IOException {
		
		try {
			User user = DBLayer.getUser(email.getText(), password.getText());
			if (user == null) {
				this.message.setText("Incorrect email or password");
			}
			else {
				Session session = getRootFrame().getSession();
				session.setUser(user);
				UserHomePanel panel = new UserHomePanel();
				getRootFrame().showPanel(panel);
				panel.init();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.message.setText(e.getMessage());
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
