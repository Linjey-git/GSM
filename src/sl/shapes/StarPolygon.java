package sl.shapes;

import java.awt.*;

/*
https://web.archive.org/web/20180315183506/http://java-sl.com/shapes.html
 */
public class StarPolygon extends Polygon {
	/**
	 * Creates a StarPolygon with specified parameters.
	 *
	 * @param x The x-coordinate of the center of the star.
	 * @param y The y-coordinate of the center of the star.
	 * @param r The outer radius of the star.
	 * @param innerR The inner radius of the star.
	 * @param vertexCount The number of vertices in the star.
	 */
	public StarPolygon(int x, int y, int r, int innerR, int vertexCount) {
		this(x, y, r, innerR, vertexCount, 0);
	}

	/**
	 * Creates a StarPolygon with specified parameters, including a start angle.
	 *
	 * @param x The x-coordinate of the center of the star.
	 * @param y The y-coordinate of the center of the star.
	 * @param r The outer radius of the star.
	 * @param innerR The inner radius of the star.
	 * @param vertexCount The number of vertices in the star.
	 * @param startAngle The start angle for the star.
	 */
	public StarPolygon(int x, int y, int r, int innerR, int vertexCount, double startAngle) {
		super(
			getXCoordinates(x, y, r, innerR, vertexCount, startAngle),
			getYCoordinates(x, y, r, innerR, vertexCount, startAngle),
			vertexCount * 2
		);
	}

	/**
	 * Generates x-coordinates for a StarPolygon with specified parameters.
	 *
	 * @param x The x-coordinate of the center of the star.
	 * @param y The y-coordinate of the center of the star.
	 * @param r The outer radius of the star.
	 * @param innerR The inner radius of the star.
	 * @param vertexCount The number of vertices in the star.
	 * @param startAngle The start angle for the star.
	 *
	 * @return An array containing the x-coordinates of the star.
	 */
	protected static int[] getXCoordinates(int x, int y, int r, int innerR, int vertexCount, double startAngle) {
		int res[] = new int[vertexCount * 2];
		double addAngle = 2 * Math.PI / vertexCount;
		double angle = startAngle;
		double innerAngle = startAngle + Math.PI / vertexCount;
		for (int i = 0; i < vertexCount; i++) {
			res[i * 2] = (int) Math.round(r * Math.cos(angle)) + x;
			angle += addAngle;
			res[i * 2 + 1] = (int) Math.round(innerR * Math.cos(innerAngle)) + x;
			innerAngle += addAngle;
		}
		return res;
	}

	/**
	 * Generates y-coordinates for a StarPolygon with specified parameters.
	 *
	 * @param x The x-coordinate of the center of the star.
	 * @param y The y-coordinate of the center of the star.
	 * @param r The outer radius of the star.
	 * @param innerR The inner radius of the star.
	 * @param vertexCount The number of vertices in the star.
	 * @param startAngle The start angle for the star.
	 *
	 * @return An array containing the y-coordinates of the star.
	 */
	protected static int[] getYCoordinates(int x, int y, int r, int innerR, int vertexCount, double startAngle) {
		int res[] = new int[vertexCount * 2];
		double addAngle = 2 * Math.PI / vertexCount;
		double angle = startAngle;
		double innerAngle = startAngle + Math.PI / vertexCount;
		for (int i = 0; i < vertexCount; i++) {
			res[i * 2] = (int) Math.round(r * Math.sin(angle)) + y;
			angle += addAngle;
			res[i * 2 + 1] = (int) Math.round(innerR * Math.sin(innerAngle)) + y;
			innerAngle += addAngle;
		}
		return res;
	}
}