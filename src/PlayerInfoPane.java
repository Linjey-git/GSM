import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public class PlayerInfoPane extends JPanel implements InfoPane {

	private Player player;

	private JLabel labSerialNumber;

	private Pictogram pictogramShow;

	private JLabel labName;

	private JLabel labCurrentPosition;

	private JLabel labCountSteps;

	private Color selectedColor = new Color(4, 245, 19, 171);
	private final Color deSelectedColor = new Color(238, 238, 238);

	/**
	 * Constructs a PlayerInfoPane with a GridBagLayout and specific borders for components.
	 */
	public PlayerInfoPane() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	/**
	 * Generates GridBagConstraints with specified parameters for a single-column component.
	 *
	 * @param column The column index.
	 * @return GridBagConstraints for a single-column component.
	 */
	private GridBagConstraints getLayoutConstraints(int column) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 1.0;
		c.gridx = column;
		c.gridy = 0;
		return c;
	}

	/**
	 * Generates GridBagConstraints with specified parameters for a component spanning multiple columns.
	 *
	 * @param column    The column index.
	 * @param gridwidth The number of columns to span.
	 * @return GridBagConstraints for a component spanning multiple columns.
	 */
	private GridBagConstraints getLayoutConstraints(int column, int gridwidth) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = gridwidth;
		c.weightx = 0.8;
		c.weighty = 1.0;
		c.gridx = column;
		c.gridy = 0;
		return c;
	}

	/**
	 * Creates the graphical user interface for the PlayerInfoPane, including labels and pictogram display.
	 */
	@Override
	public void createGUI() {
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

		JPanel sn = new JPanel(new BorderLayout());
		labSerialNumber = new JLabel("s/n");
		labSerialNumber.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		labSerialNumber.setHorizontalAlignment(JLabel.CENTER);
		labSerialNumber.setOpaque(true);
		sn.add(labSerialNumber, BorderLayout.CENTER);
		this.add(sn, getLayoutConstraints(0));

		JPanel content = new JPanel(new GridLayout());
		content.setLayout(new GridLayout(1, 4));

		pictogramShow = new Pictogram();
		pictogramShow.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		content.add(pictogramShow);

		labName = new JLabel("player ...");
		labName.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		content.add(labName);

		labCurrentPosition = new JLabel("0");
		labCurrentPosition.setHorizontalAlignment(JLabel.CENTER);
		labCurrentPosition.setBorder(BorderFactory.createLineBorder(Color.RED));
		content.add(labCurrentPosition);

		labCountSteps = new JLabel("0");
		labCountSteps.setHorizontalAlignment(JLabel.CENTER);
		labCountSteps.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		content.add(labCountSteps);

		this.add(content, getLayoutConstraints(1, 5));
		this.setOpaque(true);
	}

	/**
	 * Sets the visual representation of the PlayerInfoPane based on the player's selection status.
	 */
	public void setSelectedPane() {
		System.out.println("Color bg infoPane info - " + this.getBackground());
		this.setBorder(null);

		if (Objects.nonNull(player) && this.player.isSelected()) {
			System.out.println("Color bg label info - " + labSerialNumber.getBackground());
			labSerialNumber.setBackground(selectedColor);
			this.setBorder(BorderFactory.createDashedBorder(Color.GREEN));
		} else {
			labSerialNumber.setBackground(deSelectedColor);
			this.setBorder(BorderFactory.createRaisedBevelBorder());
		}
	}

	/**
	 * Displays the player's name on the PlayerInfoPane.
	 *
	 * @param name The name of the player.
	 */
	public void showPlayerName(final String name) {
		labName.setText(name);
	}

	/**
	 * Displays the player's position on the PlayerInfoPane.
	 *
	 * @param position The position of the player.
	 */
	public void showPlayerPosition(final String position) {
		labCurrentPosition.setText(position);
	}

	/**
	 * Displays the player's steps on the PlayerInfoPane.
	 *
	 * @param steps The number of steps taken by the player.
	 */
	public void showPlayerSteps(final String steps) {
		labCountSteps.setText(steps);
	}

	/**
	 * Displays the player's pictogram based on the provided Painter.
	 *
	 * @param painter The Painter representing the pictogram.
	 */
	public void showPlayerPictogram(Painter painter) {
		pictogramShow.setPictogramPainter(painter);
	}

	/**
	 * Displays the player's pictogram based on the provided string representation.
	 *
	 * @param pictogram The string representation of the pictogram.
	 */
	public void showPlayerPictogram(final String pictogram) {
		pictogramShow.setText(pictogram);
	}

	/**
	 * Displays the serial number on the PlayerInfoPane.
	 *
	 * @param serialNumber The serial number to be displayed.
	 */
	public void showSerialNumber(final String serialNumber) {
		labSerialNumber.setText(serialNumber);
	}

	/**
	 * Displays the serial number on the PlayerInfoPane.
	 *
	 * @param serialNumber The serial number to be displayed.
	 */
	public void showSerialNumber(final Integer serialNumber) {
		labSerialNumber.setText(String.valueOf(serialNumber));
	}
	/**
	 * Sets the associated player for the PlayerInfoPane and updates its displayed information.
	 *
	 * @param player The Player to be associated with the PlayerInfoPane.
	 */
	@Override
	public void setPlayer(final Player player) {
		this.player = player;
		showInfo();
	}

	/**
	 * Retrieves the associated player with the PlayerInfoPane.
	 *
	 * @return The associated Player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Updates the visual representation of the PlayerInfoPane based on the associated player's information.
	 */
	@Override
	public void showInfo() {
		this.setSelectedPane();

		if (Objects.nonNull(player)) {
			this.showSerialNumber("" + player.getSerialNumber());
			this.showPlayerPictogram(player.getPictogramPainter());
			this.showPlayerName(player.getPlayerName());
			this.showPlayerPosition("" + (player.getPlayerPos() + 1));
			this.showPlayerSteps("" + player.getCountSteps());
		} else {
			this.showPlayerPictogram("");
			this.showPlayerName("");
			this.showPlayerPosition("");
			this.showPlayerSteps("");
		}
	}
}