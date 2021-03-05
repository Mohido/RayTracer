import java.awt.Dimension;


import javax.swing.JFrame;

import renderer.Screen;
import renderer.demoPanel;
import utils.Vector;
import utils.shapes.Sphere;

public class Window {
	
	private JFrame win;
	private int width, height;
	static Screen s;
	private Window(int width , int height) { 
		this.win = new JFrame("RayTracer"); 
		this.width = width;
		this.height = height;
		this.win.setPreferredSize(new Dimension(width, height));
		this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.win.setResizable(false);
		this.win.setVisible(true);
		
		
		///adding components
		s = new Screen(this.width, this.height);
		this.win.getContentPane().add(s);
		
		///packing and setting the position of the frame
		this.win.pack();
		this.win.setLocationRelativeTo(null);
	}
	
	public void render() {
		this.s.render();
	}
	
	public static void main(String[] args) {
		Window win = new Window(900, 600);
		//for(int x = 0 ; x< 5 ; x++) {
		long before = System.currentTimeMillis();
		win.render();
		win.render();
		long duration = System.currentTimeMillis() - before;
		
		System.out.println(duration);
		//}
		
	}

}
