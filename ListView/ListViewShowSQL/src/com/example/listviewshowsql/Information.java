package com.example.listviewshowsql;

public class Information {
	private String id;
	private String name;
	private String phone;

	@Override
	public String toString() {
		return "Information [id=" + id + ", name=" + name + ", phone=" + phone
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
