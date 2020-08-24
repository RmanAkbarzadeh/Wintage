package ir.kn.entity;

import java.util.HashMap;
import java.util.Map;

public class Session {

	private User user;
	private Map<Object, Object> attributes = new HashMap<>();
	
	public void addAttribute(Object key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(Object key) {
		return attributes.get(key);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
