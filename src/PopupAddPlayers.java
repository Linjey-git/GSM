import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2023-11-29
 **/
public class PopupAddPlayers extends JDialog implements ActionListener, ChangeListener {
	private final List<Player> playerList = new ArrayList<>();
	private int countPlayers;

	private int currentPlayer;
	private ButtonGroup buttonGroup;
	private JTextField txtPlayerName;

	public PopupAddPlayers() {
		this((JFrame) null);
	}


	/**
	 * Constructor for the PopupAddPlayers class.
	 *
	 * @param frame The parent JFrame for the dialog.
	 */
	public PopupAddPlayers(JFrame frame) {
		super(frame, "Add players", true);
		createGUI();
		// Center the dialog relative to the parent frame
		setLocationRelativeTo(frame);

		currentPlayer = -1;
		countPlayers = 2;
	}

	/**
	 * Creates the graphical user interface for the PopupAddPlayers dialog.
	 */
	private void createGUI() {
		this.setLayout(new BorderLayout());

		// Combo for count players
		JPanel panelChoiceCountPlayers = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panelChoiceCountPlayers, BoxLayout.X_AXIS);
		panelChoiceCountPlayers.setLayout(boxLayout);
		panelChoiceCountPlayers.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 2));

		final JLabel labelCountPlayers = new JLabel("Choice count players:", SwingConstants.RIGHT);
		labelCountPlayers.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 5));
		panelChoiceCountPlayers.add(labelCountPlayers);

		JComboBox<Integer> cboxCountPlayers = new JComboBox<>(new Integer[]{2, 3, 4, 5});
		// https://stackoverflow.com/a/19642731
		((JLabel) cboxCountPlayers.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		cboxCountPlayers.addActionListener(this);
		panelChoiceCountPlayers.add(cboxCountPlayers);

		this.getContentPane().add(panelChoiceCountPlayers, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 2));
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);

		// Panel for input name player
		JPanel panelNamePlayer = new JPanel();
		panelNamePlayer.setLayout(new BoxLayout(panelNamePlayer, BoxLayout.X_AXIS));

		final JLabel labelNamePlayer = new JLabel("Input player name:", SwingConstants.RIGHT);
		labelNamePlayer.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 5));
		panelNamePlayer.add(labelNamePlayer);

		txtPlayerName = new JTextField(10);
		panelNamePlayer.add(txtPlayerName);

		centerPanel.add(panelNamePlayer);

		// Panel for choice pictogram player
		JPanel panelPictograms = new JPanel(new GridBagLayout());

		// http://www.java2s.com/Tutorial/Java/0240__Swing/0180__ButtonGroup.htm
		buttonGroup = new ButtonGroup();
		JRadioButton aRadioButton;
		String[] caption = {"STAR", "SQUARE", "CIRCLE", "TRIANGLE", "DIAMOND"};

		for (int i = 0; i < 5; i++) {
			aRadioButton = new JRadioButton(caption[i]);
			aRadioButton.setActionCommand("" + i);
			aRadioButton.addChangeListener(this);
			addPictogramWithComponent(panelPictograms, i, aRadioButton, 0, i);
			buttonGroup.add(aRadioButton);
		}

		centerPanel.add(panelPictograms);

		// Panel for control buttons
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		JButton ok = new JButton("Add");
		ok.setPreferredSize(cancel.getPreferredSize());
		ok.addActionListener(this);

		JPanel buttons = new JPanel();
		buttons.add(cancel);
		buttons.add(ok);
		getContentPane().add(buttons, BorderLayout.SOUTH);

		this.setSize(400, 320);
		this.setResizable(false);
	}

	// https://stackoverflow.com/a/21958470

	/**
	 * Adds a label with a component to the specified container at the given position.
	 *
	 * @param componentTo The container to which the label and component are added.
	 * @param text        The text for the label.
	 * @param component   The component to be added.
	 * @param x           The x-coordinate position.
	 * @param y           The y-coordinate position.
	 */
	private void addLabelWithComponent(JComponent componentTo, String text, JComponent component, int x, int y) {
		addLabel(componentTo, new JLabel(text), x, y);
		addComponent(componentTo, component, x + 1, y);
	}

	/**
	 * Adds a pictogram with a component to the specified container at the given position.
	 *
	 * @param componentTo     The container to which the pictogram and component are added.
	 * @param numberPictogram The number representing the pictogram.
	 * @param component       The component to be added.
	 * @param x               The x-coordinate position.
	 * @param y               The y-coordinate position.
	 */
	private void addPictogramWithComponent(
		JComponent componentTo,
		int numberPictogram,
		JComponent component,
		int x,
		int y
	) {
		addLabel(componentTo, Pictogram.getInstance(numberPictogram), x + 1, y);
		addComponent(componentTo, component, x, y);
	}

	/**
	 * Adds a component to the specified container at the given position.
	 *
	 * @param componentTo The container to which the component is added.
	 * @param component   The component to be added.
	 * @param x           The x-coordinate position.
	 * @param y           The y-coordinate position.
	 */
	private void addComponent(JComponent componentTo, JComponent component, int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		componentTo.add(component, gbc);
	}

	/**
	 * Adds a label to the specified container at the given position.
	 *
	 * @param componentTo The container to which the label is added.
	 * @param component   The label to be added.
	 * @param x           The x-coordinate position.
	 * @param y           The y-coordinate position.
	 */
	private void addLabel(JComponent componentTo, JComponent component, int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		int left = x == 2 ? 35 : 5;
		gbc.insets = new Insets(5, left, 5, 5);
		componentTo.add(component, gbc);
	}

	/**
	 * Handles the action events triggered by buttons or combo boxes.
	 *
	 * @param e The ActionEvent object.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() instanceof JComboBox<?> cbox) {
			countPlayers = (int) cbox.getSelectedItem();
			System.out.println("count of players is %d".formatted(countPlayers));
		} else if (e.getSource() instanceof JButton button) {
			handleButtonAction(button);
		}
	}

	/**
	 * Performs specific actions based on the button pressed.
	 *
	 * @param button The JButton triggering the action.
	 */
	private void handleButtonAction(JButton button) {
		switch (button.getText()) {
			case "Cancel" -> this.setVisible(false);
			case "Add" -> {
				createPlayer();
				clearForm();
				countPlayers--;
				if (countPlayers == 0) {
					button.setText("Start");
				}
			}
			case "Start" -> this.setVisible(false);
		}
		System.out.println(button.getText());
	}

	/**
	 * Clears the form by deselecting radio buttons and resetting the player name field.
	 */
	private void clearForm() {
		buttonGroup.clearSelection();
		txtPlayerName.setText("");
	}

	/**
	 * Creates a new player based on user input and adds it to the player list.
	 */
	private void createPlayer() {
		final int numberPainter;
		final var selection = buttonGroup.getSelection();
		if (Objects.isNull(selection)) {
			numberPainter = SelkiesGui.generateRandomNumber(0, 4);
		} else {
			numberPainter = Integer.parseInt(selection.getActionCommand());
		}
		Player player = new Player(Painter.getPainterByIndex(numberPainter));
		player.setPlayerName(txtPlayerName.getText());
		this.playerList.add(player);
	}

	/**
	 * Responds to state changes, currently not implemented.
	 *
	 * @param e The ChangeEvent object.
	 */
	@Override
	public void stateChanged(final ChangeEvent e) {
		// Add necessary logic if needed
	}

	/**
	 * Gets the list of players created during the session.
	 *
	 * @return The list of Player objects.
	 */
	public List<Player> getPlayers() {
		return this.playerList;
	}

	/**
	 * Gets the selected radio button from a ButtonGroup.
	 *
	 * @param group The ButtonGroup containing radio buttons.
	 * @return The selected JRadioButton or null if none selected.
	 */
	public static JRadioButton getSelection(ButtonGroup group) {
		for (Enumeration<AbstractButton> e = group.getElements(); e.hasMoreElements(); ) {
			JRadioButton b = (JRadioButton) e.nextElement();
			if (group.isSelected(b.getModel())) {
				return b;
			}
		}
		return null;
	}
}