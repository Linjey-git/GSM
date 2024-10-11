import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public class ObstaclePane extends JPanel {

	private List<Obstacle> obstacleList = new ArrayList<>();

	/**
	 * Constructs an ObstaclePane.
	 */
	public ObstaclePane() {
		super();
		setOpaque(false);
	}

	/**
	 * Adds an obstacle to the ObstaclePane.
	 *
	 * @param obstacle The obstacle to be added.
	 */
	public void addObstacle(final Obstacle obstacle) {
		obstacle.setObstaclePane(this);
		obstacleList.add(obstacle);
	}

	/**
	 * Removes an obstacle from the ObstaclePane.
	 *
	 * @param obstacle The obstacle to be removed.
	 */
	public void removeObstacle(final Obstacle obstacle) {
		obstacleList.remove(obstacle);
	}

	/**
	 * Clears all obstacles from the ObstaclePane.
	 */
	public void clearObstacle() {
		obstacleList.clear();
	}

	/**
	 * Overrides the paintComponent method to draw obstacles.
	 *
	 * @param g The Graphics object to draw on.
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (!obstacleList.isEmpty()) {
			obstacleList.forEach(o -> o.draw(g));
		}
	}
}