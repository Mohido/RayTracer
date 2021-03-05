package utils;

public class Vector {
	public double x,y,z;
	public double length;
	
	public Vector (double x, double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = Math.sqrt(x*x + y*y + z*z);
		//this.normalise();
	}
	public Vector (Vector a) {
		this.x = a.x;
		this.y = a.y;
		this.z = a.z;
		this.length = a.length;
		//this.normalise();
	}
	public Vector () {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.length = 0;
		//this.normalized = null;
	}
	
	/**
	 * Adds 2 vectors normally e.g: (a.x + b.x , a.y + b.y ...)
	 * 
	 * @param a - The second vector to add to the main one
	 * @return - Returns a new vector with the solution to the addition
	 */
	public Vector add (Vector a)						 {	return new Vector(a.x + this.x , a.y + this.y, a.z + this.z);}
	/**
	 * Adds 2 vectors normally e.g: (a.x + b.x , a.y + b.y ...)
	 * 
	 * @param a - The first vector
	 * @param b - The second vector
	 * @return - Returns a new vector with the solution to the addition
	 */
	public static Vector add (Vector a, Vector b) 	{	return new Vector(a.x + b.x , a.y + b.y, a.z + b.z);}
	
	
	/**
	 * Adds 2 vectors normally e.g: (a.x - b.x , a.y - b.y ...)
	 * 
	 * @param a - The second vector to add to the main one
	 * @return - Returns a new vector with the solution to the addition
	 */
	public Vector sub (Vector a)						 {	return new Vector( this.x - a.x , this.y - a.y, this.z -  a.z);}
	/**
	 * sub vector b from vector a e.g: (a.x - b.x , a.y - b.y ...)
	 * 
	 * @param a - The first vector
	 * @param b - The second vector
	 * @return - Returns a new vector with the solution to the addition
	 */
	public static Vector sub (Vector a, Vector b) 	{	return new Vector(a.x - b.x , a.y - b.y, a.z - b.z);}
	
	
	 
	/**
	 * Multiplication of 2 vectors e.g a.x*b.x , a.y*b.y ....
	 * 
	 * @return - Returns a new vector that contains the result of the multiplication.
	 */
	public Vector mul (Vector a) 					{ 		return new Vector(a.x * this.x , a.y * this.y, a.z * this.z); }
	/**
	 * Multiplication of 2 vectors e.g a.x*b.x , a.y*b.y ....
	 * 
	 * @return - Returns a new vector that contains the result of the multiplication.
	 */
	public static Vector mul (Vector a, Vector b) 	{		return new Vector(a.x * b.x , a.y * b.y, a.z * b.z);}
	
	public static Vector mul (Vector a, double b) 	{		return new Vector(a.x * b , a.y * b, a.z * b);}
	public Vector mul (double a) 	{		return new Vector(a * this.x , a * this.y, a * this.z);}
	
	
	/**
	 * This method will normalise the vectoro and assign the normalized value to a normalized vector variable.
	 */
	public void normalise() 
	{	
		if(this.length != 1.0 && this.length != 0.0) {
			this.x = this.x / this.length;
			this.y = this.y / this.length;
			this.z = this.z / this.length;
			this.length = 1.0;
		}else {
			//System.out.println(" Vector " + this.toString() + " Can't be normalized the length is either 1 or 0");
		}
	}
	
	
	///arithmatic way of fifinding the dot prod of 2 vectors
	public static double dotProdA (Vector a , Vector b) 	{	return (a.x*b.x + a.y*b.y + a.z*b.z);}
	public double dotProdA (Vector a) 				   		{ 	return (a.x*this.x + a.y*this.y + a.z*this.z);}
	
	
	///Arithmatic way of finding the cross product of 2 vectors. 
	public static Vector crossProdA (Vector a , Vector b) {
		double xx = a.y*b.z - a.z*b.y;
		double yy = a.x*b.z - a.z*b.x;
		double zz = a.x*b.y - a.y*b.x;
		return new Vector(xx,yy,zz);
	}
	public Vector crossProdA (Vector a ) {
		double xx = a.y*this.z - a.z*this.y;
		double yy = a.x*this.z - a.z*this.x;
		double zz = a.x*this.y - a.y*this.x;
		return new Vector(xx,yy,zz);
	}
	
	@Override
	public String toString() 				{ 	return ("<" + this.x + ", " + this.y + ", " + this.z + ">");}
	
}
