package renderer;

import java.util.ArrayList;

import utils.Vector;
import utils.shapes.LightSphere;
import utils.shapes.Plane;
import utils.shapes.ReflectSphere;
import utils.shapes.Sphere;

public class Camera {
	Vector origin;
	Vector camDir; ///forward direction..
	Vector v1,v2; ///2 directional vectors for the ray to shoot at...
	double fov;
	double h,w;
	
	private Scene scene;
	
	///trying hitting light and rendering
	//ArrayList<Sphere> lights = new ArrayList<Sphere>();
	
	private int maxBounces = 5;
	
	
	///in radians
	public Camera(Vector origin, Vector dir, double fov, double aspectRatio, Scene s){
		this.origin = origin;
		this.camDir = dir;
		this.scene = s;
		this.camDir.normalise();
		
		///Check if they are not normalized; but they respect eachothers ratio
		this.h = Math.tan(fov); ///y not tan(fov) * 1/aspectRatio?
		this.w = this.h * aspectRatio;
		this.fov = fov;
		
		
		///order matters .. Using right hand rule
		this.v1 = this.camDir.crossProdA(new Vector(0,1,0));	 /// u =  forwardVector * y-axis
		this.v2 = this.v1.crossProdA(this.camDir); 				/// v =  u x forwardVector
		
		System.out.println("Camera created with: height: "+ this.h + " , width: " + this.w);
		System.out.println("Vector u = " + this.v1.toString() + " Vector v = " + this.v2.toString() + " Vector depth = " + this.camDir.toString());
	}
	
	
	//[-1,1] x and y 
	///Note this keeps bouncing light until it finds a light source or reach the maximum depth
	public Vector calculatePixel(double xx , double yy) {
		Vector right = this.v1.mul(xx*this.w);
		Vector up = this.v2.mul(yy*this.h);
		Vector sumRU = right.add(up);
		Vector rayDir = this.camDir.add(sumRU);
		rayDir.normalise();
		return this.shootRay(this.origin, rayDir, 0);
		
	}
	
	
	
//===================== Tracing Methods
	
	private Vector shootRay(Vector rayOrigin, Vector rayDirection , int depth) {
		
		
		if(depth >= this.maxBounces) {
			return new Vector();
		}
///--------------------------------------- searching spheres intersections.
		int sphereIndex = -1;
		Sphere s = null;
		Vector sphereHitPoint = null;
		for(int i = 0; i < scene.getStaticSpheresCount() ; i++) {
			Vector hit = scene.getStaticSphere(i).intersect(rayOrigin, rayDirection);
			if(hit != null) { ///if a ray to a sphere hit
				Sphere temp = scene.getStaticSphere(i);
				if(sphereHitPoint == null) { ///if we hit the first sphere in the array
					s = temp; 		//sure its a sphere
					sphereHitPoint = hit;  //hitpoint is set
					sphereIndex = i;
					
				}else {			///if we already have a hitpoint already, we check if it is better to substitute it with the newest value
					if(sphereHitPoint.sub(rayOrigin).length > hit.sub(rayOrigin).length) {
						sphereHitPoint = hit;
						s = temp;
						sphereIndex = i;
					}
				}
			}
		} // searching closest sphere
///--------------------------------------- searching closer light sources
		LightSphere light = null;
		Vector lightHitPoint = null;
		for(int i =0 ; i < scene.getLightSourcesCount(); i++) {
			Vector hit = scene.getLightSource(i).intersect(rayOrigin, rayDirection);
			if(hit != null) { ///if a ray to a sphere hit
				LightSphere temp = scene.getLightSource(i);
				if(lightHitPoint == null) { ///if we hit the first sphere in the array
					lightHitPoint = hit;  //hitpoint is set
					light = temp; 		//sure its a sphere
					
				}else {			///if we already have a hitpoint already, we check if it is better to substitute it with the newest value
					if(lightHitPoint.sub(rayOrigin).length > hit.sub(rayOrigin).length) {
						lightHitPoint = hit;
						light = temp;
					}
				}
			}
		}
		
//#done __________________________ next: Calculate Colours By tracing again____________________________
///--------------------------------------- searching closer light sources
		Plane plane = null;
		Vector planeHitPoint = null;
		for(int i = 0 ; i < scene.getPlanesCount(); i++) {
			Vector hit = scene.getPlane(i).intersect(rayOrigin, rayDirection);
			if(hit != null) { ///if a ray to a sphere hit
				Plane temp = scene.getPlane(i);
				if(planeHitPoint == null) { ///if we hit the first sphere in the array
					planeHitPoint = hit;  //hitpoint is set
					plane = temp; 		//sure its a sphere
					
				}else {			///if we already have a hitpoint already, we check if it is better to substitute it with the newest value
					if(planeHitPoint.sub(rayOrigin).length > hit.sub(rayOrigin).length) {
						planeHitPoint = hit;
						plane = temp;
					}
				}
			}
		}
		
//#done __________________________ next: Calculate Colours By tracing again____________________________
		
		if((s != null && sphereHitPoint != null) 
		 && (light != null && lightHitPoint != null)
		 && (plane != null && planeHitPoint != null)) 
		{
			double lightLength = lightHitPoint.sub(rayOrigin).length;
			double sphereLength = sphereHitPoint.sub(rayOrigin).length;
			double planeLength = planeHitPoint.sub(rayOrigin).length;
			
			if( lightLength < sphereLength && lightLength < planeLength)	
				{return light.getEmissionColour();}
			else if(sphereLength < lightLength && sphereLength < planeLength)
				{return shootFromSphere(rayOrigin, rayDirection, s, sphereHitPoint, depth);}
			else if(planeLength < sphereLength && planeLength < lightLength) 
				{return shootFromPlane(rayOrigin, rayDirection, plane, planeHitPoint, depth);}
		}
		
		else if( lightHitPoint != null && planeHitPoint == null && sphereHitPoint == null) 	///hit only light
		{ return light.getEmissionColour();}
		
		else if(sphereHitPoint != null && planeHitPoint == null && lightHitPoint == null) ///hit only sphere
			{return shootFromSphere(rayOrigin, rayDirection, s, sphereHitPoint, depth);}
		
		else if( planeHitPoint != null &&  sphereHitPoint == null && lightHitPoint == null)  // hit plane only 
			{return shootFromPlane(rayOrigin, rayDirection, plane, planeHitPoint, depth);}
		
		else if(planeHitPoint != null && sphereHitPoint != null) { /// plane and sphere
			double sphereLength = sphereHitPoint.sub(rayOrigin).length;
			double planeLength = planeHitPoint.sub(rayOrigin).length;
			
			if(planeLength < sphereLength) 
				{return shootFromPlane(rayOrigin, rayDirection, plane, planeHitPoint, depth);}
			else 
				{return shootFromSphere(rayOrigin, rayDirection, s, sphereHitPoint, depth);}
		}
		
		else if(planeHitPoint != null && lightHitPoint != null) { /// plane and lightSphere
			double lightLength = lightHitPoint.sub(rayOrigin).length;
			double planeLength = planeHitPoint.sub(rayOrigin).length;
			if( lightLength < planeLength) 
				{return light.getEmissionColour();}
			else 
				{return shootFromPlane(rayOrigin, rayDirection, plane, planeHitPoint, depth);}
		}
		
		else if(lightHitPoint != null && sphereHitPoint != null) {
			double lightLength = lightHitPoint.sub(rayOrigin).length;
			double sphereLength = sphereHitPoint.sub(rayOrigin).length;
			if( lightLength < sphereLength)	
				{return light.getEmissionColour();}
			else
				{return shootFromSphere(rayOrigin, rayDirection, s, sphereHitPoint, depth);}
		}
		
		
		return new Vector(0,0,0);
	} ///ShootRay()

	
	
	
///========================================= Shooting from methods
	//----------- plane
	public Vector shootFromPlane( Vector rayOrigin, Vector rayDirection, Plane plane , Vector planeHitPoint, int depth) {
		Vector resultColor = new Vector();

		for(int i = 0 ; i < this.scene.getLightSourcesCount() ; i++) {
			LightSphere lamp = this.scene.getLightSource(i);
			Vector rayDir = lamp.getCenter().sub(planeHitPoint);
			rayDir.normalise();
			//Vector lampHitPoint = lamp.intersect(planeHitPoint, rayDir);
			double cosi = plane.getNormal().dotProdA(rayDir);
			if( cosi <= 0) {
				continue;
			}
			
			/*boolean intersection = false;
			for(int j = 0 ; j < this.scene.getStaticSpheresCount(); j++) {
				Vector sphereHitPoint = this.scene.getStaticSphere(j).intersect(planeHitPoint, rayDir);
				Sphere s = this.scene.getStaticSphere(j);
				if(sphereHitPoint != null && sphereHitPoint.sub(planeHitPoint).length < lampHitPoint.sub(planeHitPoint).length) {
					intersection = true;
					Vector color = shootFromSphere(rayOrigin, rayDir, s, sphereHitPoint ,depth+1);
					///TODO: Edit for black and white colours, and colorfull lights.
					double avg = (color.x + color.y + color.z)/3.0; 
					resultColor =  resultColor.add(new Vector(avg,avg,avg));
				}
			}
			if(!intersection) 
			{ 
				resultColor = plane.getSurfaceColour().mul(lamp.getEmissionColour()).mul(cosi);
			}*/
			
			Vector color = shootRay(planeHitPoint, rayDir, depth + 1 );
			if(color.x > 1) color.x = 1;
			if(color.y > 1) color.y = 1;
			if(color.z > 1) color.z = 1;
			double avg = (color.x + color.y + color.z) / 3.0;
			resultColor = resultColor.add(plane.getSurfaceColour().mul(new Vector(avg,avg,avg)).mul(cosi));
		}
		
		Vector rayDir = reflectionDirection(rayDirection, plane.getNormal());
		Vector reflColor = shootRay(planeHitPoint, rayDir, depth+1); ///calculating reflection color
		Vector result =  reflColor.mul(plane.getReflectionRate()).add(resultColor);
		if(result.x > 1) {result.x = 1;}
		if(result.y > 1) {result.y = 1;}
		if(result.z > 1) {result.z = 1;}
		return result;
	}
	

	//----------- sphere
	public Vector shootFromSphere( Vector rayOrigin, Vector rayDirection, Sphere sphere, Vector sphereHitPoint , int depth) {
		Vector normal = sphere.getNormal(sphereHitPoint);
		normal.normalise();
		
		Vector resultColor = new Vector();
		
		if(sphere instanceof ReflectSphere) { /// if reflection
			for(int i = 0 ; i < this.scene.getLightSourcesCount() ; i++) {
				LightSphere lamp =this.scene.getLightSource(i);
				Vector rayDir = lamp.getCenter().sub(sphereHitPoint);
				rayDir.normalise();
				double cosi = normal.dotProdA(rayDir);
				
				if(cosi <= 0) {
					continue;
				}
				
				resultColor = resultColor.add(sphere.getSurfaceColour().mul(shootRay(sphereHitPoint , rayDir, depth+1).mul(cosi)));///result color of the surface
			}
			
			Vector rayDir = reflectionDirection(rayDirection, normal);
			Vector reflColor = shootRay(sphereHitPoint, rayDir, depth+1); ///calculating reflection color
			ReflectSphere ref = (ReflectSphere) sphere;
			Vector result =  reflColor.mul(ref.getReflectionRate()).add(resultColor);
			if(result.x > 1) {
				result.x = 1;
			}
			if(result.y > 1) {
				result.y = 1;
			}
			if(result.z > 1) {
				result.z = 1;
			}
			return result;
			
		}else { /// if static sphere
			for(int i = 0 ; i < this.scene.getLightSourcesCount() ; i++) {
				LightSphere lamp = this.scene.getLightSource(i);
				Vector rayDir = lamp.getCenter().sub(sphereHitPoint);
				rayDir.normalise();
				double cosi = normal.dotProdA(rayDir);
				
				if( cosi <= 0) {
					continue;
				}
				resultColor = resultColor.add(sphere.getSurfaceColour().mul(shootRay(sphereHitPoint , rayDir, depth+1).mul(cosi)));
			}
			
			return resultColor;
		}
	}
	
	
	
	
	
	
///==================================== Reflection Calc
	public Vector reflectionDirection(Vector rayDir, Vector normal) {
		rayDir.normalise();
		normal.normalise();
		
		double cosi2 = 2 * rayDir.dotProdA(normal);
		Vector result = new Vector(rayDir.sub(normal.mul(cosi2)));
		result.normalise();
		
		return  result;///ref = i - 2.<i.n>.n or = i - 2.cosi.n
	}
	
	
	
///==================================== Single ShadowRay //Note: unusable in the code at the moment
	public Vector shadowRay(Vector hitPoint, Sphere sph, int sphereIndex) {
		Vector normal = sph.getNormal(hitPoint);
		normal.normalise();
		
		//hitPoint = hitPoint.add(normal.mul(1E-4));
		for(int i = 0 ; i < this.scene.getLightSourcesCount() ; i++) {
			LightSphere lamp = this.scene.getLightSource(i);
			Vector rayDirection = lamp.getCenter().sub(hitPoint);
			Vector inter = lamp.intersect( hitPoint,  rayDirection);
			
			if(inter != null) {
				 
				boolean clearLight = true;
				for(int j = 0 ; j < this.scene.getStaticSpheresCount() ; j++) {
					Sphere temp = this.scene.getStaticSphere(j);
					if(j != sphereIndex) {
						Vector obstacle = temp.intersect(hitPoint, rayDirection);
						if(obstacle != null && obstacle.sub(hitPoint).length < inter.sub(hitPoint).length) {
							clearLight = false;
							return new Vector();
							//return new Vector();
						}
					}
				} 
				
				double dotPr = rayDirection.dotProdA(normal);
				if(dotPr > 0 && clearLight) {
					return lamp.getEmissionColour().mul(dotPr);
				}else {
					return new Vector();
				}
				
			}
		}
		
		return new Vector();	
	}
	
///==================================== Getters Area
	public double getHeight() { return this.h;}
	public double getWidth() {return this.w;}
	
}
