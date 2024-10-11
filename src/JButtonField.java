import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public class JButtonField extends JButton {

	private List<Player> playerList = new ArrayList<>();

	private int position;

	/**
	 * Protected constructor for JButtonField with a specified position and a list of players.
	 *
	 * @param position The position of the field.
	 * @param list The list of players associated with the field.
	 */
	protected JButtonField(final int position, List<Player> list) {
		super("%d".formatted(position + 1));
		this.position = position;
		// Temporarily filling, for view as paint pictograms
		playerList.addAll(list);
	}

	/**
	 * Protected constructor for JButtonField with a specified position.
	 *
	 * @param position The position of the field.
	 */
	protected JButtonField(final int position) {
		super("%d".formatted(position + 1));
		this.position = position;
	}

	/**
	 * Overrides the paintComponent method to draw players on the field.
	 *
	 * @param g The Graphics object to paint.
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (playerList.isEmpty()) {
			return;
		}
		Point[] points = getPointsForDrawShape();
		AtomicInteger i = new AtomicInteger(0);
		playerList.forEach(p -> p.paint(points[i.getAndIncrement()], g));
	}

	/**
	 * function calculated point with placing Player pictogram
	 *
	 * @return array {@link Point}
	 */
	private Point[] getPointsForDrawShape() {
		if (playerList.isEmpty()) {
			return null;
		}
		int vertexCount = playerList.size();
		int r = Math.min(getHeight(), getWidth()) / 2 - 10;
		Point[] res = new Point[vertexCount];
		double addAngle = 2 * Math.PI / vertexCount;
		double angle = Math.PI / -2;
		for (int i = 0; i < vertexCount; i++) {
			res[i] = new Point(
				(int) Math.round(r * Math.cos(angle)) + getWidth() / 2,
				(int) Math.round(r * Math.sin(angle)) + getHeight() / 2
			);
			angle += addAngle;
		}
		return res;
	}

	/**
	 * Gets the position of the field.
	 *
	 * @return The position.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Adds a player to the field.
	 *
	 * @param player The player to be added.
	 */
	public void addPlayer(Player player) {
		playerList.add(player);
		repaint();
	}

	/**
	 * Removes a player from the field.
	 *
	 * @param player The player to be removed.
	 */
	public void removePlayer(Player player) {
		playerList.remove(player);
		repaint();
	}

	/**
	 * Prepares the field for a new game by clearing the player list.
	 */
	public void prepareNewGame() {
		playerList.clear();
		repaint();
	}
}