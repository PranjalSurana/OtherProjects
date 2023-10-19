package com.roifmr.contactapi.controller;

import java.util.Objects;

public class RowCountDto {
	private int rowCount;
	
	public RowCountDto() { }

	public RowCountDto(int rowCount) {
		super();
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rowCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowCountDto other = (RowCountDto) obj;
		return rowCount == other.rowCount;
	}

	@Override
	public String toString() {
		return "RowCountDto [rowCount=" + rowCount + "]";
	}
	
}
