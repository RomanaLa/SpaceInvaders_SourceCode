package com.laisterboehm.collision;

public class Projection {

	public double min;
	public double max;
	
	public Projection(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean overlap(Projection p) {
		if (this.min < p.max && this.min > p.min || p.min < this.max && p.min > this.min) {
			return true;
		}
		return false;
	}
	
	public double getOverlap(Projection p) {
		if (this.overlap(p)) {
			//the smaller maximum minus the bigger minimum
			double smallerMax = Math.min(this.max, p.max);
			double biggerMin = Math.max(this.min, p.min);
			return (smallerMax - biggerMin);
			
		}
		return 0;
	}
}
