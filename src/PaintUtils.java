import sl.shapes.RegularPolygon;
import sl.shapes.StarPolygon;

import java.awt.*;
import java.util.function.BiConsumer;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public class PaintUtils {
	public static final Integer STAR = 0;
	public static final Integer SQUARE = 1;
	public static final Integer CIRCLE = 2;
	public static final Integer TRIANGLE = 3;
	public static final Integer DIAMOND = 4;

	/**
	 * Draws a square at the specified point.
	 *
	 * @param point The point to draw the square.
	 * @param g     The Graphics object for drawing.
	 */
	private static void drawSquare(Point point, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(point.x - 8, point.y - 8, 16, 16);
		g.setColor(Color.BLUE);
		g.drawRect(point.x - 8, point.y - 8, 16, 16);
	}

	/**
	 * Draws a circle at the specified point.
	 *
	 * @param point The point to draw the circle.
	 * @param g     The Graphics object for drawing.
	 */
	private static void drawCircle(Point point, Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(point.x - 8, point.y - 8, 16, 16);
		g.setColor(Color.MAGENTA);
		g.drawOval(point.x - 8, point.y - 8, 16, 16);
	}

	/**
	 * Draws a star at the specified point.
	 *
	 * @param point The point to draw the star.
	 * @param g     The Graphics object for drawing.
	 */
	private static void drawStar(Point point, Graphics g) {
		Polygon star = new StarPolygon(point.x, point.y, 8, 3, 6, Math.PI / 4);
		g.setColor(Color.MAGENTA);
		g.fillPolygon(star);
		g.setColor(Color.BLACK);
		g.drawPolygon(star);
	}

	/**
	 * Draws a triangle at the specified point.
	 *
	 * @param point The point to draw the triangle.
	 * @param g     The Graphics object for drawing.
	 */
	private static void drawTriangle(Point point, Graphics g) {
		Polygon triangle = new RegularPolygon(point.x, point.y, 8, 3, Math.PI / -2);
		g.setColor(Color.BLUE);
		g.fillPolygon(triangle);
		g.setColor(Color.RED);
		g.drawPolygon(triangle);
	}

	/**
	 * Draws a diamond at the specified point.
	 *
	 * @param point The point to draw the diamond.
	 * @param g     The Graphics object for drawing.
	 */
	private static void drawDiamond(Point point, Graphics g) {
		Polygon diamond = new RegularPolygon(point.x, point.y, 8, 4, 0);
		g.setColor(Color.PINK);
		g.fillPolygon(diamond);
		g.setColor(Color.RED);
		g.drawPolygon(diamond);
	}

	/**
	 * Gets a BiConsumer for painting based on the specified index.
	 *
	 * @param index The index to determine the painting method.
	 * @return The BiConsumer for painting.
	 */
	public static BiConsumer<Point, Graphics> getConsumer(int index) {
		return switch (index) {
			case 0 /* STAR */ -> PaintUtils::drawStar;
			case 1 /* SQUARE */ -> PaintUtils::drawSquare;
			case 3 /* TRIANGLE */ -> PaintUtils::drawTriangle;
			case 4 /* DIAMOND */ -> PaintUtils::drawDiamond;
			default /* 2 - CIRCLE */ -> PaintUtils::drawCircle;
		};
	}
}