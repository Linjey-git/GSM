import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @Author _se.ho
 * @create 2023-12-02
 **/

public class Save implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Player> playerList;

	private List<Obstacle> obstacleList;

	/**
	 * Gets the list of players in the game.
	 *
	 * @return The list of Player objects.
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Sets the list of players in the game.
	 *
	 * @param playerList The list of Player objects to set.
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * Gets the list of obstacles in the game.
	 *
	 * @return The list of Obstacle objects.
	 */
	public List<Obstacle> getObstacleList() {
		return obstacleList;
	}

	/**
	 * Sets the list of obstacles in the game.
	 *
	 * @param obstacleList The list of Obstacle objects to set.
	 */
	public void setObstacleList(List<Obstacle> obstacleList) {
		this.obstacleList = obstacleList;
	}

	/**
	 * Initializes the Save object with specified player and obstacle lists.
	 *
	 * @param playerList The list of players to set.
	 * @param obstacleList The list of obstacles to set.
	 */
	public Save(List<Player> playerList, List<Obstacle> obstacleList) {
		this.playerList = playerList;
		this.obstacleList = obstacleList;
	}

	// https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

	/**
	 * Opens a file chooser dialog to get the path of the selected file.
	 *
	 * @return The name of the selected file or null if the operation is cancelled.
	 */
	private static String getPathFile(TypeDialog typeDialog) {
		final JFileChooser fc = new JFileChooser();

		int returnVal = switch (typeDialog) {
			case OPEN_DIALOG -> fc.showOpenDialog(null);
			case SAVE_DIALOG -> fc.showSaveDialog(null);
		};

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// This is where a real application would open the file.
			System.out.println(switch (typeDialog) {
				case OPEN_DIALOG -> "Opening: ";
				case SAVE_DIALOG -> "Saving: ";
			} + file.getAbsolutePath() + ".");
			return file.getAbsolutePath();
		} else {
			System.out.println("Open command cancelled by user.");
		}
		return null;
	}

	/**
	 * Saves the current game state to a file named "save.txt".
	 */
	public void save() {
		try (
			FileOutputStream fileOut = new FileOutputStream(getPathFile(TypeDialog.SAVE_DIALOG));
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
		) {
			objectOut.writeObject(this);
		} catch (Exception e) {
			// Handle exceptions appropriately, e.g., log or print the stack trace.
			e.printStackTrace();
		}
	}

	/**
	 * Loads a saved game state from a user-selected file.
	 *
	 * @return The loaded Save object or null if loading fails.
	 */
	public static Save load() {
		Save loadedObject = null;
		try {
			ObjectInputStream
				objectIn =
				new ObjectInputStream(new FileInputStream(getPathFile(TypeDialog.OPEN_DIALOG)));
			loadedObject = (Save) objectIn.readObject();
		} catch (FileNotFoundException e) {
			// Handle file not found exception.
			e.printStackTrace();
		} catch (Exception e) {
			// Handle other exceptions, e.g., ClassNotFoundException or IOException.
			e.printStackTrace();
		}
		return loadedObject;
	}

	private enum TypeDialog {
		OPEN_DIALOG,
		SAVE_DIALOG
	}
}