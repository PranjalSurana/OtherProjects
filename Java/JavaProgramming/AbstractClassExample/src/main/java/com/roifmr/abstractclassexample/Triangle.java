package com.roifmr.abstractclassexample;

public class Triangle extends Shape {
	private double base;
	private double height;
	
	public Triangle(double x, double y, double base, double height) {
		super(x, y);
		this.base = base;
		this.height = height;
	}

	@Override
	public double getArea() {
		return base * height / 2;
	}

	public double getBase() {
		return base;
	}

	public double getHeight() {
		return height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(base);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(height);
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
		Triangle other = (Triangle) obj;
		if (Double.doubleToLongBits(base) != Double.doubleToLongBits(other.base))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Triangle [base=" + base + ", height=" + height + "]";
	}

}
