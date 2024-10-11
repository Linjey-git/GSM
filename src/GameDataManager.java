import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/

public class GameDataManager {

	private static final int DEFAULT_PLAYERS = 2;
	private static final int MAX_PLAYERS = 5;
	private static int playerNo;

	private static String[] playerNames = new String[playerNo];

	/**
	 * Gets the player number.
	 *
	 * @return The player number.
	 */
	public static int getPlayerNo() {
		return playerNo;
	}

	/**
	 * Sets the player number.
	 *
	 * @param playerNo The player number to be set.
	 */
	public static void setPlayerNo(int playerNo) {
		GameDataManager.playerNo = playerNo;
	}

	/**
	 * Gets the array of player names.
	 *
	 * @return The array of player names.
	 */
	public static String[] getPlayerNames() {
		return playerNames;
	}

	/**
	 * Sets the array of player names.
	 *
	 * @param playerNames The array of player names to be set.
	 */
	public static void setPlayerNames(String[] playerNames) {
		GameDataManager.playerNames = playerNames;
	}

	/**
	 * Opens a dialog for player number and name input.
	 */
	public static void askPlayerNoAndName() {
		JFrame frame = new JFrame("Player Input");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		JLabel label = new JLabel("Select the number of players:");
		Integer[] playerOptions = new Integer[MAX_PLAYERS - 1];
		for (int i = 0; i < playerOptions.length; i++) {
			playerOptions[i] = i + 2; // From 2 to MAX_PLAYERS
		}
		JComboBox<Integer> playerCountComboBox = new JComboBox<>(playerOptions);
		playerCountComboBox.setSelectedItem(DEFAULT_PLAYERS);

		JButton submitButton = new JButton("Submit");

		panel.add(label);
		panel.add(playerCountComboBox);
		panel.add(submitButton);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerNo = (int) playerCountComboBox.getSelectedItem();

				for (int i = 0; i < playerNo; i++) {
					String playerName = JOptionPane.showInputDialog(frame, "Enter name for Player " + (i + 1) + ":");
					if (playerName != null) {
						playerNames[i] = playerName;
					} else {
						playerNames[i] = "Player " + (i + 1); // Default name
					}
				}

				for (int i = 0; i < playerNo; i++) {
					System.out.println("Player " + (i + 1) + ": " + playerNames[i]);
				}
			}
		});

		frame.setContentPane(panel);
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}