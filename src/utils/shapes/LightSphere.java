package utils.shapes;

import utils.Vector;

public class LightSphere extends Sphere{

	private Vector emissionColour;
	
	public LightSphere(Vector origin, double radius, Vector ec, Vector sc) {
		super(origin, radius, sc);
		this.emissionColour = ec;
	}
	
	public Vector getEmissionColour() { return this.emissionColour;	}

	
}
