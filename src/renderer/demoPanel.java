package renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

public class demoPanel extends JPanel {

	int w,h;
	BufferedImage out_image;
	int[] pixels;
	int a = 0;
	
	public demoPanel(int width , int height) {
		super();
		
		this.setDoubleBuffered(true);
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.w = width;
		this.h = height;
		this.out_image = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt)this.out_image.getRaster().getDataBuffer()).getData();
		
		for (int i = 0 ; i < this.h*this.w ; i++) {
			this.pixels[i] = 0;
		}
		this.repaint();
	}
	 
	public void render() {
		for (int i = 0 ; i < this.h*this.w ; i++) {
			this.pixels[i] = a;
		}
		this.repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(out_image, 0, 0, w, h, null);
		g.dispose();
		a++;
	}
}
