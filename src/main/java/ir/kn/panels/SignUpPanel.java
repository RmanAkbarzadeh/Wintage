package ir.kn.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public class SignUpPanel extends SNJPanel implements SetMaterialGf{
	private JTextField email;
	private JTextField username;
	private JTextField bio;
	private JTextField links;
	private JTextField password;
	private JTextField name;
	private JTextField phone;
	private JLabel message;
        private JButton btnCancel;

	/**
	 * Create the panel.
	 */
	public SignUpPanel() {
		//setBackground(Color.lightGray);
                setBackground(new Color(17,33,68));
		setLayout(null);
		setSize(880, 600);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(230, 100, 499, 317);
                panel_1.setBackground(new Color(29, 161, 242));
		add(panel_1);
		panel_1.setLayout(null);
		
                JLabel nameLb = new JLabel("Name:");
		nameLb.setBounds(105, 46, 82, 14);
                nameLb.setForeground(Color.white);
                setBigFont(nameLb);
		panel_1.add(nameLb);
                
                JLabel EmailLb = new JLabel("Email:");
		EmailLb.setBounds(105, 76, 82, 14);
                EmailLb.setForeground(Color.white);
                setBigFont(EmailLb);
		panel_1.add(EmailLb);
                
                
		JLabel UsernameLb = new JLabel("Username:");
		UsernameLb.setBounds(105, 106, 82, 14);
                UsernameLb.setForeground(Color.white);
                setBigFont(UsernameLb);
		panel_1.add(UsernameLb);
		
		
		/*JLabel BioLb = new JLabel("bio:");
		BioLb.setBounds(105, 120, 82, 14);
                BioLb.setForeground(Color.white);
                setBigFont(BioLb);
		panel_1.add(BioLb);
		
		JLabel LinksLb = new JLabel("links:");
		LinksLb.setBounds(105, 145, 82, 14);
                LinksLb.setForeground(Color.white);
                setBigFont(LinksLb);
		panel_1.add(LinksLb);*/
		
		JLabel PasswordLb = new JLabel("Password:");
		PasswordLb.setBounds(105, 136, 82, 14);
                PasswordLb.setForeground(Color.white);
                setBigFont(PasswordLb);
		panel_1.add(PasswordLb);
                
                JLabel PhoneLb = new JLabel("Phone:");
		PhoneLb.setBounds(105, 166, 82, 14);
                PhoneLb.setForeground(Color.white);
                setBigFont(PhoneLb);
		panel_1.add(PhoneLb);
                
                message = new JLabel("");
		message.setBounds(50, 220, 500, 14);
                message.setForeground(Color.white);
                setBigFont(message);
		panel_1.add(message);
		
		email = new JTextField();
		email.setBounds(193, 73, 209, 20);
		panel_1.add(email);
		email.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(193, 103, 209, 20);
		panel_1.add(username);
		
		/*bio = new JTextField();
		bio.setColumns(10);
		bio.setBounds(193, 120, 209, 20);
		panel_1.add(bio);
		
		links = new JTextField();
		links.setColumns(10);
		links.setBounds(193, 145, 209, 20);
		panel_1.add(links);
		*/
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(193, 133, 209, 20);
		panel_1.add(password);
                
                name = new JTextField();
		name.setColumns(10);
		name.setBounds(193, 43, 209, 20);
		panel_1.add(name);
		
		
		phone = new JTextField();
		phone.setColumns(10);
		phone.setBounds(193, 163, 209, 20);
		panel_1.add(phone);
                
               /* JLabel lblPleaseEnterThe = new JLabel("Please enter the information for SignUp :");
		lblPleaseEnterThe.setBounds(120, 11, 335, 14);
                lblPleaseEnterThe.setForeground(Color.white);
		panel_1.add(lblPleaseEnterThe);*/
                
                JLabel lblShow = new JLabel("Please enter the information for SignUp :");
                lblShow.setForeground(Color.white);
                lblShow.setBounds(80, 0, 450, 40);             
                setBigFont(lblShow);
                panel_1.add(lblShow);
		
		JButton btnSignUp = new JButton("SignUp");
                btnSignUp.setBackground(new Color(29, 161, 242));
                btnSignUp.setForeground(Color.white);       
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
                            
                              boolean check = Validate();
                              
                                    if(check == true){
                                        signUp();
                                        btnCancel.setText("Back");
                                    }
			}
		});
		btnSignUp.setBounds(215, 240, 160, 30);
                setCircleButton(btnSignUp);
		panel_1.add(btnSignUp);
		
		btnCancel = new JButton("Cancel");
                btnCancel.setBackground(new Color(29, 161, 242));
                btnCancel.setForeground(Color.white);    
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancel();
			}
		});
		btnCancel.setBounds(215, 280, 160, 30);
                setCircleButton(btnCancel);
		panel_1.add(btnCancel);
		

		
		
		

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

	protected void signUp() {

		User user = new User();
		user.setUsername(username.getText());
		user.setEmail(email.getText());
		user.setBio(bio.getText());
		user.setName(name.getText());
		user.setPassword(password.getText());
		user.setPhone(phone.getText());
		user.setLinks(links.getText());
		
		try {
			DBLayer.registerUser(user);
			this.message.setText("Successfully Registered. Please Login to continue.");
		}
		catch (Exception e) {
			e.printStackTrace();
			this.message.setText(e.getMessage());
		}
	}

	protected void cancel() {
		getRootFrame().showPanel(new SplashPanel());
	}
        
        protected boolean Validate(){
            
            boolean check = true;
            
              if(!name.getText().matches("[A-Z][a-zA-Z]*")){
                  
                  this.message.setText("Please enter a valid name");
                  
                   check = false;
              }
              
              if(!email.getText().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
                  
                  if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",email"));

                  }else{
                                   
                        this.message.setText("Please enter a valid email");
                        check = false;
                        }
              }
              
              if(!username.getText().matches("^[a-z0-9_-]{3,15}$")){
                  
                   if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",username"));

                   }else{
                            this.message.setText("Please enter a valid username");
                             check = false;
                        }
              }
              
              if(!password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")){
                  
                   if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",password"));

                   }else{
                            this.message.setText("Please enter a valid password");
                             check = false;
                        }
              }
              
              if(!phone.getText().matches("^[0-9]{11}$")){
                  
                   if(check == false){
                      
                      this.message.setText(this.message.getText().concat(",phone."));

                   }else{
                            this.message.setText("Please enter a valid phone");
                             check = false;
                        }
              }
              
              
             return check;
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