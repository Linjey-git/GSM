import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * @Author _se.ho
 * @create 2023-11-21
 **/

//Snake
public class Selkie extends Obstacle {

    double phi;

    int barb;

    public Selkie() {
        phi = Math.toRadians(30);
        barb = 15;
    }

    /**
     * Interacts with a GameObject by setting its position to the current end position of this object.
     *
     * @param object The GameObject to interact with.
     */
    @Override
    void interactWithObject(GameObject object) {
        object.setPosX(this.getPosEndX());
        object.setPosY(this.getPosEndY());
    }

    /**
     * Draws the object with an orange color and lines connecting the start and end points.
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    void draw(Graphics g) {
        Point start = convertStartTo();
        Point end = convertEndTo();
//		g.drawLine(start.x + 2, start.y + 2, end.x + 2, end.y + 2);
//		g.drawLine(start.x + 6, start.y + 2, end.x + 6, end.y + 2);
        double[]
                controlPoints =
                calculateBezierControlPoints(
                        start.x,
                        start.y,
                        end.x,
                        end.y
                );

        CubicCurve2D curve = new CubicCurve2D.Double(
                controlPoints[0], controlPoints[1],
                controlPoints[2], controlPoints[3],
                controlPoints[4], controlPoints[5],
                controlPoints[6], controlPoints[7]
        );
        Graphics2D g2 = (Graphics2D) g;
        // get a copy of the current transform so we can restore it later
        Color saveColor = g2.getColor();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        // draw body
        g2.setStroke(new BasicStroke(
                12.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER
        ));
        g2.setColor(Color.ORANGE);
        g2.draw(curve);
// draw head
        g2.setStroke(new BasicStroke(
                8.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER
        ));
        drawArrowHead(
                g2,
                new Point((int) curve.getP1().getX(), (int) curve.getP1().getY()),
                new Point((int) curve.getP2().getX(), (int) curve.getP2().getY()),
                Color.YELLOW
        );
// draw thinker line over body
        g2.setStroke(new BasicStroke(
                3.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER
        ));
        g2.setPaint(Color.DARK_GRAY);
        g2.draw(curve);
        drawArrowHead(
                g2,
                new Point((int) curve.getP1().getX(), (int) curve.getP1().getY()),
                new Point((int) curve.getP2().getX(), (int) curve.getP2().getY()),
                Color.DARK_GRAY
        );

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
//        System.out.println("%s, start.x = %d, start.y = %d, end.x = %d, end.y = %d".formatted(this.getClass().getName(),start.x, start.y, end.x, end.y));
        g2.setColor(saveColor);
    }

    /**
     * Calculates control points for a Bezier curve based on the given start and end points.
     *
     * @param x0 The x-coordinate of the start point.
     * @param y0 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     * @return An array containing the control points for the Bezier curve.
     */
    private double[] calculateBezierControlPoints(
            double x0, double y0,
            double x2, double y2
    ) {
        // Calculate control points for the Bezier curve
        double[] controlPoints = new double[8];

        // Start point
        controlPoints[0] = x0;
        controlPoints[1] = y0;

        // Midpoint between the start and end points
        double mx = (x0 + x2) / 2.0;
        double my = (y0 + y2) / 2.0;

        // Distance between the start and end points
        double distance = Math.sqrt(Math.pow(x2 - x0, 2) + Math.pow(y2 - y0, 2));

        // Angle of the line segment between the start and end points
        double angle = Math.atan2(y2 - y0, x2 - x0);

        // Distance to shift the control points
        double offset = distance / 10.0;

        // Midpoint control points
        controlPoints[2] = mx + 1.5 * offset * Math.cos(angle + Math.PI / 2);
        controlPoints[3] = my + 1.5 * offset * Math.sin(angle + Math.PI / 2);
        controlPoints[4] = mx + 2.5 * offset * Math.cos(angle - Math.PI / 2);
        controlPoints[5] = my + 2.5 * offset * Math.sin(angle - Math.PI / 2);

        // End point
        controlPoints[6] = x2;
        controlPoints[7] = y2;

        return controlPoints;
    }

    /**
     * Draws an arrowhead on a Graphics2D context from a given tip and tail with a specified color.
     *
     * @param g2    The Graphics2D context to draw on.
     * @param tip   The point representing the arrowhead tip.
     * @param tail  The point representing the arrowhead tail.
     * @param color The color of the arrowhead.
     */
    // https://coderanch.com/t/340443/java/Draw-arrow-head-line
    private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color) {
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;
        for (int j = 0; j < 2; j++) {
            x = tip.x - barb * Math.cos(rho);
            y = tip.y - barb * Math.sin(rho);
            g2.setPaint(color);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            g2.setPaint(Color.BLACK);
            g2.draw(new Ellipse2D.Double(x, y, 2, 2));
            rho = theta - phi;
        }
    }

    /**
     * Determines whether the object needs to perform at the specified position.
     *
     * @param position The position to check against.
     * @return True if the start button's position is equal to the specified position, otherwise false.
     */
    @Override
    boolean isNeedPerformance(final int position) {
        return getStartButton().getPosition() == position;
    }
}