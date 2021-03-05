package utils.shapes;

import utils.Vector;

public interface Shape {
	public Vector intersect(Vector rayOrig, Vector rayDir);
}
