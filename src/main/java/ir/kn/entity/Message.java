package ir.kn.entity;

import java.util.Date;

public class Message {
	
	private int id;
	private String text;
	private User sender;
	private int receiverType;
	private Date sendingTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public int getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}
	public Date getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(Date sendingTime) {
		this.sendingTime = sendingTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
