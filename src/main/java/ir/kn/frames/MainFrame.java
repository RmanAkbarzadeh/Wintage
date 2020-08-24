package ir.kn.frames;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ir.kn.panels.PublicMessagesPanel;
import ir.kn.entity.Session;
import ir.kn.panels.SplashPanel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private Session session = new Session();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainFrame frame = new MainFrame();
				frame.setSize(990, 670);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
                showPanel(new SplashPanel());
	}
	
	public void showPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		
		panel.setSize(this.getWidth(), this.getHeight());
		
		this.validate();
		this.repaint();
		panel.setVisible(true);
		
	}

	public Session getSession() {
		return session;
	}
}
