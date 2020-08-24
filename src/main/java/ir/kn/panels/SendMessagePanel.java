package ir.kn.panels;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import ir.kn.dblayer.DBLayer;
import ir.kn.entity.Message;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.border.Border;

public class SendMessagePanel extends SNJPanel implements SetMaterialGf{
	
	private JRadioButton rdbtnForPublic;
	private JRadioButton rdbtnForSubscribersOnly;
	private JLabel message;
	private JTextPane text;
	
	public SendMessagePanel() {
		setLayout(null);
		setSize(860, 600);
		text = new JTextPane();
		text.setBounds(146, 86, 574, 116);
		add(text);
		
		JLabel lblEnterMessage = new JLabel("Enter a Message:");
                lblEnterMessage.setForeground(Color.white);
		lblEnterMessage.setBounds(146, 50, 200, 25);
                setBigFont(lblEnterMessage);
		add(lblEnterMessage);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBackground(new Color(29, 161, 242));
                btnSend.setForeground(Color.white);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendMessage();
			}
		});
		btnSend.setBounds(320, 286, 200, 40);
                setCircleButton(btnSend);
		add(btnSend);
		
		rdbtnForPublic = new JRadioButton("For Public");
                rdbtnForPublic.setBackground(new Color(29, 161, 242));
                rdbtnForPublic.setForeground(Color.white);
		rdbtnForPublic.setSelected(true);
                rdbtnForPublic.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 22));
		rdbtnForPublic.setBounds(170, 220, 250, 23);
		add(rdbtnForPublic);
		
		rdbtnForSubscribersOnly = new JRadioButton("For Subscribers");
                rdbtnForSubscribersOnly.setBackground(new Color(29, 161, 242));
                rdbtnForSubscribersOnly.setForeground(Color.white);
                rdbtnForSubscribersOnly.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 22));
		rdbtnForSubscribersOnly.setBounds(500, 220, 250, 23);
		add(rdbtnForSubscribersOnly);
		
		ButtonGroup grp = new ButtonGroup();
		grp.add(rdbtnForPublic);
		grp.add(rdbtnForSubscribersOnly);
		
		message = new JLabel("");
		message.setBounds(530, 261, 190, 14);
		add(message);
	}

	protected void sendMessage() {
		
		boolean forPublic = true;
		
		if (rdbtnForSubscribersOnly.isSelected()) {
			forPublic = false;
		}
		
		Message msg = new Message();
		msg.setSender(getRootFrame().getSession().getUser());
		msg.setReceiverType(forPublic ? 1 : 2);
		msg.setSendingTime(new Date());
		
		String str = text.getText().trim();
		msg.setText(str);
		
		if (str.length() <= 0 || str.length() > 130) {
			message.setText("Must length must be 1-130");
			return;
		}
		
		try {
			DBLayer.sendMessage(msg);
			message.setText("Successfully sent.");
			text.setText("");
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
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
                jbutton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    }
}
