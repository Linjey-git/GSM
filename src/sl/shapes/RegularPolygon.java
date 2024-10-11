package sl.shapes;

import java.awt.*;

/*
https://web.archive.org/web/20180315183506/http://java-sl.com/shapes.html
 */
public class RegularPolygon extends Polygon {
	/**
	 * Constructs a RegularPolygon with a specified center, radius, vertex count, and start angle.
	 * The polygon is created based on the given parameters.
	 *
	 * @param x The x-coordinate of the center.
	 * @param y The y-coordinate of the center.
	 * @param r The radius of the polygon.
	 * @param vertexCount The number of vertices in the polygon.
	 */
	public RegularPolygon(int x, int y, int r, int vertexCount) {
		this(x, y, r, vertexCount, 0);
	}

	/**
	 * Constructs a RegularPolygon with a specified center, radius, vertex count, and start angle.
	 * The polygon is created based on the given parameters.
	 *
	 * @param x The x-coordinate of the center.
	 * @param y The y-coordinate of the center.
	 * @param r The radius of the polygon.
	 * @param vertexCount The number of vertices in the polygon.
	 * @param startAngle The starting angle for creating the polygon.
	 */
	public RegularPolygon(int x, int y, int r, int vertexCount, double startAngle) {
		super(
			getXCoordinates(x, y, r, vertexCount, startAngle),
			getYCoordinates(x, y, r, vertexCount, startAngle),
			vertexCount
		);
	}

	/**
	 * Calculates the x-coordinates of the vertices of the regular polygon.
	 *
	 * @param x The x-coordinate of the center.
	 * @param y The y-coordinate of the center.
	 * @param r The radius of the polygon.
	 * @param vertexCount The number of vertices in the polygon.
	 * @param startAngle The starting angle for creating the polygon.
	 *
	 * @return An array of x-coordinates of the vertices.
	 */
	protected static int[] getXCoordinates(int x, int y, int r, int vertexCount, double startAngle) {
		int res[] = new int[vertexCount];
		double addAngle = 2 * Math.PI / vertexCount;
		double angle = startAngle;
		for (int i = 0; i < vertexCount; i++) {
			res[i] = (int) Math.round(r * Math.cos(angle)) + x;
			angle += addAngle;
		}
		return res;
	}

	/**
	 * Calculates the y-coordinates of the vertices of the regular polygon.
	 *
	 * @param x The x-coordinate of the center.
	 * @param y The y-coordinate of the center.
	 * @param r The radius of the polygon.
	 * @param vertexCount The number of vertices in the polygon.
	 * @param startAngle The starting angle for creating the polygon.
	 *
	 * @return An array of y-coordinates of the vertices.
	 */
	protected static int[] getYCoordinates(int x, int y, int r, int vertexCount, double startAngle) {
		int res[] = new int[vertexCount];
		double addAngle = 2 * Math.PI / vertexCount;
		double angle = startAngle;
		for (int i = 0; i < vertexCount; i++) {
			res[i] = (int) Math.round(r * Math.sin(angle)) + y;
			angle += addAngle;
		}
		return res;
	}
}