package com.example.boot.entity;

public class Listform {

	private String name;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Listform(String name, String status) {
		super();
		this.name = name;
		this.status = status;
	}

	public Listform() {
		super();
	}

	@Override
	public String toString() {
		return "Listform [name=" + name + ", status=" + status + "]";
	}

}
