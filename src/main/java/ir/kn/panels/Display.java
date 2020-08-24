package ir.kn.panels;

import ir.kn.entity.Message;

import javax.swing.*;
import java.util.List;

public interface Display {

    void displayMessages(List<Message> messages, JTable table);

}
