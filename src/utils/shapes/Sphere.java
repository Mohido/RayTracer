package utils.shapes;

import utils.Vector;

public class Sphere implements Shape{

	Vector origin;
	Vector surfaceColour;
	double radius;
	
	
	
	public Sphere(Vector origin, double radius, Vector sc) {
		this.origin = origin;
		this.radius = radius;
		this.surfaceColour = sc;
	}
	
	
	
	/**
	 * This function uses the geometric way in gettin gthe intersection points of a line and a circle.
	 * 
	 * @param rayOrig - The ray origin vector.
	 * @param rayDir - The ray direction vector. Make sure it is normalised.
	 */
	@Override
	public Vector intersect(Vector rayOrig, Vector rayDir) {
		rayDir.normalise(); 
		
		/*Vector offsetedCenter = this.getCenter().sub(rayOrig); 
        double tca = offsetedCenter.dotProdA(rayDir); 
        if (tca < 0) return null;
        
        double d2 = offsetedCenter.dotProdA(offsetedCenter) - tca * tca; 
        if (d2 > this.radius*this.radius) return null; 
        double thc = Math.sqrt(this.radius*this.radius - d2); 
        double t0 = tca - thc; 
        double t1 = tca + thc;
        
        if(t0 < t1) {
        	return new Vector(rayOrig.add(rayDir.mul(t0)));
        }else {
        	return new Vector(rayOrig.add(rayDir.mul(t1)));
        }*/
 
  ///________________________________________
		Vector sphere_offseted_center = Vector.sub(this.origin , rayOrig ); 					///changing the sphere coordinates to see the ray origin as the real origin for it
		double scaler = Vector.dotProdA(sphere_offseted_center , rayDir);  					 	///scaler of the ray equation
		if(scaler < 0 )
			return null;
		Vector pp = Vector.add( 	rayOrig,/* + */Vector.mul(rayDir, scaler)	); 			///projection point,....  r = rayOrigin + scaler*rayDirection
		
		//System.out.println("projection of the circle is: " + pp.toString() + " and scaler is: " + scaler);
		
		double xx = (pp.x - this.origin.x) * (pp.x - this.origin.x);
		double yy = (pp.y - this.origin.y) * (pp.y - this.origin.y);
		double zz = (pp.z - this.origin.z) * (pp.z - this.origin.z);
		double hpoint_to_center_length = Math.sqrt( xx + yy + zz ); /// |sphereOrigin, hitPoint| distance
		//System.out.println("length of t: " + hpoint_to_center_length);
		
		if(hpoint_to_center_length == this.radius) {
			return pp;
			
		}else if(hpoint_to_center_length < this.radius) {
			double hitPointsDifference = Math.sqrt(this.radius*this.radius - hpoint_to_center_length*hpoint_to_center_length);
			
			double scaler1 = scaler + hitPointsDifference;
			Vector hp1 = Vector.add( 		rayOrig,/* + */Vector.mul(rayDir, scaler1)	); ///first hitpoint
			
			double scaler2 =  scaler - hitPointsDifference;
			Vector hp2 = Vector.add( 		rayOrig,/* + */Vector.mul(rayDir, scaler2)	); ///second hitpoint
			
			//System.out.println("good 2 hit:  " + hp1.toString() + " and " + hp2.toString());
			
			double hp1Length = hp1.sub(rayOrig).length; 	///offseting the points to the ray origin to find their length compared to the origin
			double hp2Length = hp2.sub(rayOrig).length;
			
			if(hp1Length <= hp2Length) {
				if(scaler1 <= 0) {
					//return null;
				}
				return hp1;
			}else {
				if(scaler2 <= 0) {
					//return null;
				}
				return hp2;
			}
			
		}
		 
		return null;
	}

	
	public Vector getNormal(Vector hitPoint) {
		Vector normal = hitPoint.sub(this.origin);
		normal.normalise();
		return normal;
	}

	public Vector getSurfaceColour() {return this.surfaceColour;	}
	
	public Vector getCenter() {return this.origin;}
	
	public boolean equals(Sphere s) {
		return this.getCenter().x == s.getCenter().x && this.getCenter().y == s.getCenter().y && this.getCenter().z == s.getCenter().z
				&& this.radius == s.radius 
				&& this.surfaceColour.x == s.surfaceColour.x && this.surfaceColour.y == s.surfaceColour.y && this.surfaceColour.z == s.surfaceColour.z;
	}
}











