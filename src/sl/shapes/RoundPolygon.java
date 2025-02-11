package sl.shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
https://web.archive.org/web/20180315183506/http://java-sl.com/shapes.html
 */
public class RoundPolygon implements Shape {
	GeneralPath path;

	/**
	 * Constructs a RoundPolygon based on the provided Polygon and arc width.
	 *
	 * @param p The original Polygon to be transformed.
	 * @param arcWidth The width of the arc to be applied during transformation.
	 */
	public RoundPolygon(Polygon p, int arcWidth) {
		path = new GeneralPath();
		transform(p, arcWidth, path);
	}

	/**
	 * Gets the bounding rectangle of the RoundPolygon.
	 *
	 * @return The bounding rectangle of the RoundPolygon.
	 */
	public Rectangle getBounds() {
		return path.getBounds();
	}

	/**
	 * Gets the bounding rectangle of the RoundPolygon in double precision.
	 *
	 * @return The bounding rectangle of the RoundPolygon in double precision.
	 */
	public Rectangle2D getBounds2D() {
		return path.getBounds2D();
	}

	/**
	 * Checks if the RoundPolygon contains the specified coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 *
	 * @return True if the RoundPolygon contains the point; otherwise, false.
	 */
	public boolean contains(double x, double y) {
		return path.contains(x, y);
	}

	/**
	 * Checks if the RoundPolygon contains the specified Point2D.
	 *
	 * @param p The Point2D.
	 *
	 * @return True if the RoundPolygon contains the point; otherwise, false.
	 */
	public boolean contains(Point2D p) {
		return path.contains(p);
	}

	/**
	 * Checks if the RoundPolygon intersects the specified rectangle.
	 *
	 * @param x The x-coordinate of the rectangle.
	 * @param y The y-coordinate of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 *
	 * @return True if the RoundPolygon intersects the rectangle; otherwise, false.
	 */
	public boolean intersects(double x, double y, double w, double h) {
		return path.intersects(x, y, w, h);
	}

	/**
	 * Checks if the RoundPolygon intersects the specified Rectangle2D.
	 *
	 * @param r The Rectangle2D.
	 *
	 * @return True if the RoundPolygon intersects the rectangle; otherwise, false.
	 */
	public boolean intersects(Rectangle2D r) {
		return path.intersects(r);
	}

	/**
	 * Checks if the RoundPolygon contains the specified rectangle.
	 *
	 * @param x The x-coordinate of the rectangle.
	 * @param y The y-coordinate of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 *
	 * @return True if the RoundPolygon contains the rectangle; otherwise, false.
	 */
	public boolean contains(double x, double y, double w, double h) {
		return path.contains(x, y, w, h);
	}

	/**
	 * Checks if the RoundPolygon contains the specified Rectangle2D.
	 *
	 * @param r The Rectangle2D.
	 *
	 * @return True if the RoundPolygon contains the rectangle; otherwise, false.
	 */
	public boolean contains(Rectangle2D r) {
		return path.contains(r);
	}

	/**
	 * Gets the PathIterator for the RoundPolygon with the specified AffineTransform.
	 *
	 * @param at The AffineTransform to be applied.
	 *
	 * @return The PathIterator for the RoundPolygon.
	 */
	public PathIterator getPathIterator(AffineTransform at) {
		return path.getPathIterator(at);
	}

	/**
	 * Gets the PathIterator for the RoundPolygon with the specified AffineTransform and flatness.
	 *
	 * @param at The AffineTransform to be applied.
	 * @param flatness The flatness parameter that affects the precision of the path.
	 *
	 * @return The PathIterator for the RoundPolygon.
	 */
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return path.getPathIterator(at, flatness);
	}

	/**
	 * Transforms the given Polygon by applying rounded corners with the specified arc width.
	 *
	 * @param shape The original Polygon to be transformed.
	 * @param arcWidth The width of the arc to be applied.
	 * @param path The GeneralPath to store the transformed shape.
	 */
	protected static void transform(Polygon shape, int arcWidth, GeneralPath path) {
		PathIterator pIter = shape.getPathIterator(new AffineTransform());

		Point2D.Float pointFirst = new Point2D.Float(0, 0);
		Point2D.Float pointSecond = null;

		Point2D.Float pointLast = new Point2D.Float(0, 0);
		Point2D.Float pointCorner = null;
		Point2D.Float pointNext = null;

		float[] coor = new float[6];
		while (!pIter.isDone()) {
			int type = pIter.currentSegment(coor);
			float x1 = coor[0];
			float y1 = coor[1];
			float x2 = coor[2];
			float y2 = coor[3];
			float x3 = coor[4];
			float y3 = coor[5];

			switch (type) {
				case PathIterator.SEG_CLOSE:
					// path.closePath();
					break;
				case PathIterator.SEG_CUBICTO:
					path.curveTo(x1, y1, x2, y2, x3, y3);
					break;
				case PathIterator.SEG_LINETO:
					if (pointCorner == null) {
						pointCorner = new Point2D.Float(x1, y1);
						if (pointNext == null) {
							// first move
							pointSecond = new Point2D.Float(x1, y1);
							Point2D.Float arcStartPoint = getArcPoint(pointSecond, pointFirst, arcWidth);

							path.moveTo(arcStartPoint.x, arcStartPoint.y);
						}
					} else {
						pointNext = new Point2D.Float(x1, y1);
						add(path, pointLast, pointCorner, pointNext, arcWidth);
						pointLast = pointCorner;
						pointCorner = pointNext;
					}
					break;
				case PathIterator.SEG_MOVETO:
					pointLast.x = x1;
					pointLast.y = y1;
					pointFirst.x = x1;
					pointFirst.y = y1;
					break;
				case PathIterator.SEG_QUADTO:
					path.quadTo(x1, y1, x2, y2);
					break;
			}
			pIter.next();
		}

		add(path, pointLast, pointCorner, pointFirst, arcWidth);
		add(path, pointCorner, pointFirst, pointSecond, arcWidth);
		path.closePath();
	}

	/**
	 * Adds a rounded corner to the GeneralPath.
	 *
	 * @param path The GeneralPath to add the corner.
	 * @param point1 The first point of the corner.
	 * @param point2 The corner point.
	 * @param point3 The second point of the corner.
	 * @param arcWidth The width of the arc.
	 */
	private static void add(
		GeneralPath path,
		Point2D.Float point1,
		Point2D.Float point2,
		Point2D.Float point3,
		int arcWidth
	) {
		Point2D.Float arcStartPoint = getArcPoint(point1, point2, arcWidth);
		Point2D.Float arcEndPoint = getArcPoint(point3, point2, arcWidth);
		path.lineTo(arcStartPoint.x, arcStartPoint.y);
		path.quadTo(point2.x, point2.y, arcEndPoint.x, arcEndPoint.y);
	}

	/**
	 * Calculates the coordinates of a point on the arc between two given points.
	 *
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @param w The width of the arc.
	 *
	 * @return The calculated point on the arc.
	 */
	protected static Point2D.Float getArcPoint(Point2D.Float p1, Point2D.Float p2, float w) {
		Point2D.Float res = new Point2D.Float();
		float d = Math.round(Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)));

		if (p1.x < p2.x) {
			res.x = p2.x - w * Math.abs(p1.x - p2.x) / d;
		} else {
			res.x = p2.x + w * Math.abs(p1.x - p2.x) / d;
		}

		if (p1.y < p2.y) {
			res.y = p2.y - w * Math.abs(p1.y - p2.y) / d;
		} else {
			res.y = p2.y + w * Math.abs(p1.y - p2.y) / d;
		}

		return res;
	}

	/**
	 * Prints the transformation of a Polygon, segment by segment.
	 *
	 * @param shape The Polygon to be transformed and printed.
	 */
	protected static void transformPrint(Polygon shape) {
		PathIterator pIter = shape.getPathIterator(new AffineTransform());
		GeneralPath res = new GeneralPath();

		float[] coor = new float[6];
		while (!pIter.isDone()) {
			int type = pIter.currentSegment(coor);
			float x1 = coor[0];
			float y1 = coor[1];
			float x2 = coor[2];
			float y2 = coor[3];
			float x3 = coor[4];
			float y3 = coor[5];

			switch (type) {
				case PathIterator.SEG_CLOSE:
					res.closePath();
					System.out.println("SEG_CLOSE");
					break;
				case PathIterator.SEG_CUBICTO:
					res.curveTo(x1, y1, x2, y2, x3, y3);
					System.out.println("SEG_CUBICTO");
					break;
				case PathIterator.SEG_LINETO:
					res.lineTo(x1, y1);
					System.out.println("SEG_LINETO");
					break;
				case PathIterator.SEG_MOVETO:
					res.moveTo(x1, y1);
					System.out.println("SEG_MOVETO");
					break;
				case PathIterator.SEG_QUADTO:
					res.quadTo(x1, y1, x2, y2);
					System.out.println("SEG_QUADTO");
					break;
			}
			pIter.next();
		}
	}

	/**
	 * Main method for testing the transformPrint and getArcPoint methods.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		Polygon p = new Polygon(new int[]{0, 100, 0}, new int[]{0, 0, 100}, 3);
		transformPrint(p);

		System.out.println("mid=" + getArcPoint(new Point2D.Float(0, 0), new Point2D.Float(50, 100), 10));
		System.out.println("mid=" + getArcPoint(new Point2D.Float(100, 100), new Point2D.Float(0, 0), 10));
	}
}