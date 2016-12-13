package cz.cvut.fjfi.pvs.pvs2016.model;

import java.io.Serializable;

public class Series implements Serializable {

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Series series = (Series) o;

		if (index != series.index) return false;
		return name.equals(series.name);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + index;
		return result;
	}

}
