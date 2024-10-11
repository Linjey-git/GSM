import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @Author _se.ho
 * @create 2023-11-21
 **/

public abstract class Obstacle extends GameObject implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int startPosition;

	protected int endPosition;

	protected JButtonField startButton;

	protected JButtonField endButton;

	protected JPanel obstaclePane;

	private int posEndX;

	private int posEndY;

	/**
	 * Gets the ending X-coordinate of the object.
	 *
	 * @return The ending X-coordinate.
	 */
	public int getPosEndX() {
		return posEndX;
	}

	/**
	 * Sets the ending X-coordinate of the object.
	 *
	 * @param posEndX The ending X-coordinate to be set.
	 */
	public void setPosEndX(int posEndX) {
		this.posEndX = posEndX;
	}

	/**
	 * Gets the ending Y-coordinate of the object.
	 *
	 * @return The ending Y-coordinate.
	 */
	public int getPosEndY() {
		return posEndY;
	}

	/**
	 * Sets the ending Y-coordinate of the object.
	 *
	 * @param posEndY The ending Y-coordinate to be set.
	 */
	public void setPosEndY(int posEndY) {
		this.posEndY = posEndY;
	}

	/**
	 * Gets the starting position of the object.
	 *
	 * @return The starting position.
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * Sets the starting position of the object.
	 *
	 * @param startPosition The starting position to be set.
	 */
	public void setStartPosition(final int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Gets the ending position of the object.
	 *
	 * @return The ending position.
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * Sets the ending position of the object.
	 *
	 * @param endPosition The ending position to be set.
	 */
	public void setEndPosition(final int endPosition) {
		this.endPosition = endPosition;
	}

	/**
	 * Abstract method to draw the object.
	 *
	 * @param graphics The Graphics object to draw on.
	 */
	abstract void draw(Graphics graphics);

	/**
	 * Abstract method to check if performance is needed for a given position.
	 *
	 * @param position The position to check.
	 * @return True if performance is needed, false otherwise.
	 */
	abstract boolean isNeedPerformance(int position);

	/**
	 * Translates the coordinates of the button, to which the obstacle is attached, into coordinates of the panel
	 * where the obstacle image will be drawn.
	 *
	 * @return
	 */
	public Point convertStartTo() {
		return convertStartTo(obstaclePane);
	}

	/**
	 * For drawing. Converts the coordinates of the button to which the obstacle is attached into coordinates of the panel
	 * where the obstacle image will be drawn.
	 *
	 * @param component
	 * @return
	 */
	public Point convertStartTo(JComponent component) {
		Point location = startButton.getLocation();
		location.x += startButton.getWidth()/3;
		location.y += startButton.getHeight()/3*2;
		return SwingUtilities.convertPoint(startButton.getParent(), location, component);
	}

	/**
	 * For drawing. Converts the coordinates of the button to which the obstacle is attached into coordinates of the panel
	 * where the obstacle image will be drawn.
	 *
	 * @return
	 */
	public Point convertEndTo() {
		return convertEndTo(obstaclePane);
	}

	/**
	 * For drawing. Converts the coordinates of the button to which the obstacle is attached into coordinates of the panel
	 * where the obstacle image will be drawn.
	 *
	 * @param component The button to which the obstacle is attached.
	 * @return
	 */
	public Point convertEndTo(JComponent component) {
		Point location = endButton.getLocation();
		location.x += endButton.getWidth()/3;
		location.y += endButton.getHeight()/3*2;
		return SwingUtilities.convertPoint(endButton.getParent(), location, component);
	}

	/**
	 * Returns the initial button. For a ladder, it's the button with a lower number, for a selkie - with a higher number.
	 * This is tracked in SelkiesGui.createObstacle.
	 *
	 * @return
	 */
	public JButtonField getStartButton() {
		return startButton;
	}

	/**
	 * Sets the initial button.
	 *
	 * @param startButton The button to be set as the initial button.
	 */
	public void setStartButton(final JButtonField startButton) {
		this.startButton = startButton;
		this.startPosition = startButton.getPosition();

		setPosX(startButton.getX());
		setPosY(startButton.getY());
	}

	/**
	 * •	Returns the final button. For a ladder, it’s the button with a lower number, for a selkie - with a higher number.
	 * •	This is tracked in SelkiesGui.createObstacle.
	 * <p>
	 * •	@return
	 */
	public JButtonField getEndButton() {
		return endButton;
	}

	/**
	 * •	Sets the final button. This is tracked in SelkiesGui.createObstacle.
	 * <p>
	 * •	@return
	 */
	public void setEndButton(final JButtonField endButton) {
		this.endButton = endButton;
		this.endPosition = endButton.getPosition();

		setPosEndX(endButton.getX());
		setPosEndY(endButton.getY());
	}

	/**
	 * Gets the obstacle panel.
	 *
	 * @return The obstacle panel.
	 */
	public JPanel getObstaclePane() {
		return obstaclePane;
	}

	/**
	 * Sets the obstacle panel.
	 *
	 * @param obstaclePane The obstacle panel to be set.
	 */
	public void setObstaclePane(final JPanel obstaclePane) {
		this.obstaclePane = obstaclePane;
	}

	/**
	 * Prepares for a new game by resetting the obstacle panel, start button, and end button to null.
	 */
	public void prepareNewGame() {
		this.obstaclePane = null;
		this.startButton = null;
		this.endButton = null;
	}
}
