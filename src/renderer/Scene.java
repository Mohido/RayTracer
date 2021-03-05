package renderer;

import java.util.ArrayList;

import utils.shapes.LightSphere;
import utils.shapes.Plane;
import utils.shapes.Sphere;

public class Scene {
	private ArrayList<Sphere> staticSpheres;
	private ArrayList<LightSphere> lightSpheres;
	private ArrayList<Plane> planes;
	
	
	public Scene(){
		this.staticSpheres = new ArrayList<Sphere>();
		this.lightSpheres = new ArrayList<LightSphere>();
		this.planes = new ArrayList<Plane>();
	}

	
	//sphere
	public void addSphere(Sphere s) {this.staticSpheres.add(s);}
	public Sphere getStaticSphere(int index) {return this.staticSpheres.get(index);}
	public int getStaticSpheresCount() {return this.staticSpheres.size();}
	
	//lightsource
	public void addLightSource(LightSphere s) {this.lightSpheres.add(s);}
	public LightSphere getLightSource(int index) {return this.lightSpheres.get(index);}
	public int getLightSourcesCount() {return this.lightSpheres.size();}
	
	//plane
	public void addPlane(Plane p) {this.planes.add(p);}
	public Plane getPlane(int i) { return this.planes.get(i);}
	public int getPlanesCount() {return this.planes.size();}
	
	
}
