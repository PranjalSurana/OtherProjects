package com.roifmr.abstractclassexample;

public class Rectangle extends Shape {
	private double length;
	private double width;

	public Rectangle(double x, double y, double length, double width) {
		super(x, y);
		this.length = length;
		this.width = width;
	}

	@Override
	public double getArea() {
		return length * width;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(length);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rectangle other = (Rectangle) obj;
		if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
			return false;
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rectangle [length=" + length + ", width=" + width + "]";
	}

}
