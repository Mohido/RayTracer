package utils.shapes;

import utils.Vector;

public class ReflectSphere extends Sphere{
	double reflectionRate;
	
	public ReflectSphere(Vector origin, double radius, Vector sc, double reflectionRate) {
		super(origin, radius, sc);
		this.reflectionRate = reflectionRate;
	}

	public double getReflectionRate() {return this.reflectionRate;};
}
