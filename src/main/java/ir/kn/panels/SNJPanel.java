package ir.kn.panels;

import ir.kn.entity.Message;
import ir.kn.frames.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

abstract class SNJPanel extends JPanel implements Display {
	
	MainFrame getRootFrame() {
		return (MainFrame) SwingUtilities.getRoot(this);
	}

	public void displayMessages(List<Message> messages, JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		for (Message m : messages) {
			model.addRow(new Object[]{m.getSender().getName(), m.getText(), m.getSendingTime()});
		}

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(450);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
	}

	public void displayMessages(List<Message> messages) {
	}

}
