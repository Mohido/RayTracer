package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import utils.Vector;
import utils.shapes.LightSphere;
import utils.shapes.Plane;
import utils.shapes.ReflectSphere;
import utils.shapes.Sphere;


public class Screen extends Canvas {
	private static final long serialVersionUID = 1L;
	
	
	
	///@TODO: height/ width ratio for the ray shooter and precise height, width ray shooter
	private BufferedImage out_image;
	private double aspectRatio;
	private int h, w;
	private int[] pixels;
	
	private Camera cam;
	private Scene scene;
	
	
	///@TODO: Build array of spheres.
	
	
	public Screen(int width, int height) {
		this.w = width;
		this.h = height;
		this.aspectRatio =  (float)width / (float)height;
		
		
		this.scene = new Scene(); ///TODO: Adding a plane to the scene
//		this.scene.addSphere(
//				new Sphere(
//						new Vector(-1,2,6), 
//						1,
//						new Vector(0.7,0.5,0.8) 
//						)
//				);
//		this.scene.addSphere(
//				new Sphere(
//						new Vector(1.5,2,6), 
//						1,
//						new Vector(0.7,0.3,0.2)
//						)
//				);
//		this.scene.addSphere(
//				new ReflectSphere(
//						new Vector(-3.5,2,6), 
//						1,
//						new Vector(0.4,0.4,0.35), 1
//						)
//				);
//		
//		this.scene.addSphere(
//				new ReflectSphere(
//						new Vector(0,-0.5,6), 
//						0.7,
//						new Vector(0.2,0.2,0.2), 1
//						)
//				);
		/*
		
		this.scene.addPlane(
				new Plane(
						new Vector(0,-1,0),
						new Vector(0,1,0), new Vector(0.6,0.6,0.6), 0
						)
				);
		this.scene.addPlane(
				new Plane(
						new Vector(0,5,0),
						new Vector(0,-1,0), new Vector(0.6,0.6,0.6), 0
						)
				);
		
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(-0.5,1,4), 
						0.2,
						new Vector(0.2,0.2,0.2), 1
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(-0.75,1.5,4), 
						0.2,
						new Vector(0.8,0.4,0.3), 0
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(-0.25,0.5,4), 
						0.2,
						new Vector(0.8,0.8,0.8), 0
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(-1.25,0.5,4), 
						0.2,
						new Vector(0.2,0.2,0.4), 0
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(-1,1,4), 
						0.2,
						new Vector(0.2,0.2,0.2), 1
						)
				);
		
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(0,1,4), 
						0.2,
						new Vector(0.2,0.2,0.2), 1
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(0.5,1,4), 
						0.2,
						new Vector(0.2,0.2,0.2), 1
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(0.23,1.5,4), 
						0.2,
						new Vector(0.2,0.4,0.3), 0
						)
				);
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(0.75,0.5,4), 
						0.2,
						new Vector(0.4,0.4,0.6), 0
						)
				);
		
		*/
		
		
		
		this.scene.addPlane(
				new Plane(
						new Vector(0,-1,0), new Vector(0,1,0),
						new Vector(0.2,0.2,0.2), 1
						)
				);
		
		this.scene.addSphere(
				new ReflectSphere(
						new Vector(1,-0.4,3), 
						0.5,
						new Vector(0.2,0.2,0.2), 1
						)
				);
		
		this.scene.addLightSource(
				new LightSphere(
						new Vector(0,0,3),
						0.1,
						new Vector(1,1,1), new Vector(1,1,1)
						)
				);
		
		
		this.cam = new Camera(new Vector(0, 0, -1), new Vector(0,0,1), Math.PI/5.0, this.aspectRatio , this.scene);
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		this.addKeyListener( new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					scene.getLightSource(0).getCenter().y = scene.getLightSource(0).getCenter().y + 0.1;
					render();
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					scene.getLightSource(0).getCenter().y = scene.getLightSource(0).getCenter().y - 0.1;
					render();
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					scene.getLightSource(0).getCenter().x = scene.getLightSource(0).getCenter().x + 0.1;
					render();
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					scene.getLightSource(0).getCenter().x = scene.getLightSource(0).getCenter().x - 0.1;
					render();
				}
				if(e.getKeyCode() == KeyEvent.VK_W) {
					scene.getLightSource(0).getCenter().z = scene.getLightSource(0).getCenter().z + 0.1;
					render();
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					scene.getLightSource(0).getCenter().z = scene.getLightSource(0).getCenter().z - 0.1;
					render();
				}
				//System.out.println(scene.getLightSource(0).getCenter().toString());
			}
			
		});
		
		this.out_image = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt)this.out_image.getRaster().getDataBuffer()).getData();
			
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		for(int x = 0 ; x < this.w ; x++) {
			double xx = 2 * (float)x / this.w - 1;  
			for(int y = 0; y < this.h ; y++) {
				double yy = 2 * (float)y / this.h - 1;
				Vector result = this.cam.calculatePixel(xx, yy);
				int colour = 0;
				colour += ( (int)(255*result.x) << 8);
				colour = ( colour +  (int)(255*result.y) ) << 8;
			    colour = ( colour +  (int)(255*result.z) ) ;
				
				this.pixels[x + y*this.w] = colour;
			}
		}
		
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.out_image, 0, 0, this.w, this.h, null);
		System.out.println("rendering the image");
		g.dispose();
		bs.show();
	}
	
	
	///@TODO .. build the tracer method here...
}
