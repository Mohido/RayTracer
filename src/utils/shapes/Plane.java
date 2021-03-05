package utils.shapes;

import utils.Vector;

public class Plane implements Shape{
	
	
	double reflectionRate;
	Vector surfaceColor;
	Vector somePoint;
	Vector normal;
	
	public Plane(Vector mainPoint, Vector normal, Vector sc ,double reflectionRate ) {
		normal.normalise();
		this.somePoint = mainPoint;
		this.surfaceColor = sc;
		this.reflectionRate = reflectionRate;
		this.normal = normal;
	}
	
	@Override
	public Vector intersect(Vector rayOrig, Vector rayDir) {
		double don = rayDir.dotProdA(normal);
		if(don == 0)
			return null;
		double scaler = ( 1.0 / don )
							*
						(somePoint.dotProdA(normal) - rayOrig.dotProdA(normal));
		if(scaler <= 0)
			return null;
		
		return rayOrig.add(rayDir.mul(scaler));
	}

	public Vector getNormal() { return this.normal; }
	public Vector getSurfaceColour() { return this.surfaceColor;}
	public double getReflectionRate() {return this.reflectionRate;}

}
