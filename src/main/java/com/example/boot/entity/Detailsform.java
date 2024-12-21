package com.example.boot.entity;

public class Detailsform {

	private String actioncode;

	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	public Detailsform(String actioncode) {
		super();
		this.actioncode = actioncode;
	}

	public Detailsform() {
		super();
	}

	@Override
	public String toString() {
		return "Detailsform [actioncode=" + actioncode + "]";
	}

}
