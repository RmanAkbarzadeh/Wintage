package ir.kn.panels;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.io.IOException;

public class UserHomePanel extends SNJPanel {

	private InboxPanel Inboxpanel;
        private SendMessagePanel sendMessagePanel;
        private JPanel searchUsersPanel;
	private ProfilePanel profilePanel;
        private PublicMessagesPanel publicMessagesPanel;

	
	/**
	 * Create the panel.
	 */
	public UserHomePanel() {
		setBackground(new Color(17,33,68));
		setForeground(new Color(29, 161, 242));
		setLayout(null);
		setSize(880, 600);
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 20, 960, 590);
		add(tabbedPane_1);
                
		publicMessagesPanel = new PublicMessagesPanel();
                publicMessagesPanel.setBackground(new Color(29, 161, 242));  
		tabbedPane_1.addTab("Public Messages", null,publicMessagesPanel,null);
                
                
                Inboxpanel = new InboxPanel();
                Inboxpanel.setBackground(new Color(29, 161, 242));          
		tabbedPane_1.addTab("Inbox", null, Inboxpanel, null);
		
		sendMessagePanel = new SendMessagePanel();
                sendMessagePanel.setBackground(new Color(29, 161, 242));
		tabbedPane_1.addTab("Send Message", null, sendMessagePanel, null);
		
		searchUsersPanel = new SearchUsersPanel();
                searchUsersPanel.setBackground(new Color(29, 161, 242));
		searchUsersPanel.setSize(600, 400);
		tabbedPane_1.addTab("Search Users", null, searchUsersPanel, null);
		
		profilePanel = new ProfilePanel();
                profilePanel.setBackground(new Color(29, 161, 242));
		tabbedPane_1.addTab("Profile", null, profilePanel, null);
		
		JButton btnNewButton = new JButton("Log Out");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getRootFrame().showPanel(new LogInPanel());
			}
		});
		btnNewButton.setBounds(781, 11, 90, 23);
		add(btnNewButton);
	
		
	}
	
	public void init() throws IOException {
		Inboxpanel.init();
		profilePanel.init();
	}
}
