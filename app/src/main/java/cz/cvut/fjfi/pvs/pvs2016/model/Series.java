package cz.cvut.fjfi.pvs.pvs2016.model;

import java.io.Serializable;

public class Series implements Serializable {

	private static final long serialVersionUID = 2402436870814270576L;

	private String name;
	private int index;

	public Series(String name) {
		this.name = name;
	}

	public Series(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
