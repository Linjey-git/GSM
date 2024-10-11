import java.awt.*;

/**
 * @Author _se.ho
 * @create 2023-11-21
 **/

//Ladder
public class Munro extends Obstacle {
	private static final long serialVersionUID = 1L;

	/**
	 * Overrides the interactWithObject method to set the position of another GameObject to the current object's
	 * position.
	 *
	 * @param object The GameObject to interact with.
	 */
	@Override
	void interactWithObject(GameObject object) {
		object.setPosX(this.getPosX());
		object.setPosY(this.getPosY());
	}

	/**
	 * Overrides the draw method to draw a specific shape in magenta color.
	 *
	 * @param g The Graphics object to draw on.
	 */
	@Override
	void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		Color saveColor = g2.getColor();

		Point start = convertStartTo();
		Point end = convertEndTo();

		// Draw baseline Ladder
		g.setColor(Color.MAGENTA);
		g2.setStroke(new BasicStroke(12.0f));
		g2.drawLine(start.x, start.y, end.x, end.y);

		// Draw dashed line, emulate stairs
		g.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke(
				3.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				1.0f,
				new float[]{25.0f, 5.0f},
				0.0f
		));
		g2.drawLine(start.x, start.y, end.x, end.y);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		// Uncomment the following line for debugging purposes
		// System.out.println("%s, start.x = %d, start.y = %d, end.x = %d, end.y = %d".formatted(this.getClass().getName(),start.x, start.y, end.x, end.y));

		g2.setColor(saveColor);
	}

	/**
	 * Overrides the isNeedPerformance method to check if performance is needed for a given position.
	 *
	 * @param position The position to check.
	 *
	 * @return True if performance is needed, false otherwise.
	 */
	@Override
	boolean isNeedPerformance(final int position) {
		return getStartButton().getPosition() == position;
	}
}
