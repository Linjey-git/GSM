/**
 * SelkiesGUI (based on an earlier ConnectGUI created by I. Martin, UoD)
 *
 * @author Iain Martin, Craig Ramsay
 * @version 1.0
 * <p>
 * Notes to use SelkiesGui
 * SelkiesGui is intended as a replacement for a Menu class for the
 * Selkies and Munros project.
 * This is optional and would be given credit as an optional extra if
 * you make a good job of using and adapting it.
 * However, please feel free to implement your own GUI in any way you wish
 * to. The intention of this example is not to restrict you in any way
 * but to give you a head-start if you wish to play with GUI code and
 * are not sure how to get started.
 * <p>
 * Comments that start with the phrase 'SelkiesGui' indicate where you
 * might add your own code. Please do not attempt to use this GUI until
 * you have already met the core requirement of the project which is to
 * developed a text-based / console version of the game.
 * <p>
 * Notes:
 * Event handlers have been set up for Menu Options
 * NewGame, LoadGame and Save Game.
 * <p>
 * An Event handler has also been set up for a Mouse Click on
 * the grid which calls clickSquare(row, col), setting it to a specific
 * colour.
 * <p>
 * To add functionality to this GUI, add your code to these functions
 * which are at the end of this file.
 * <p>
 * Potential additions: FileChoosers could be implemented and the grid characters
 * could be replaced with graphics by loading gifs or jpgs into the grid which is
 * created from JButtons.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SelkiesGui implements ActionListener {

    // Default filename to use for saving and loading files
    // Possible improvement: replace with a FileChooser
    private final static String DEFAULT_FILENAME = "selkiesgui.txt";
    private static JFrame mainFrame;
    private final int GRID_SIZE_X = 10;
    private final int GRID_SIZE_Y = 10;
    private JButtonField[] buttonArray;

    private JPanel leftPanel = new JPanel(new BorderLayout());
    private JPanel rightPanel = new JPanel(new BorderLayout());

    private List<Player> playerList;

    private List<PlayerInfoPane> playerPaneList = new ArrayList<>();
    private JComponent infoPane;

    private List<Obstacle> obstacleList = new ArrayList<>();

    private ObstaclePane obstaclePane;

    private JComponent dicePane;

    private static final Random random = new Random();

    private static int diceValue;
    private Map<Integer, Player> playerMap;

    /**
     * Create the GUI and show it.
     * For thread safety, this method should be invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {

        Dimension boardSize = new Dimension(1100, 900);
        // Create and set up the window.
        mainFrame = new JFrame("SelkiesGui");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        SelkiesGui selkiesGUI = new SelkiesGui();
        mainFrame.setJMenuBar(selkiesGUI.createMenu());
//		JPanel contentPane = new JPanel(new GridLayout());
//		contentPane.setOpaque(true);

        mainFrame.setContentPane(selkiesGUI.createContentPane(boardSize));
        JPanel glass = (JPanel) mainFrame.getGlassPane();
        glass.setLayout(new BorderLayout());
        glass.add(selkiesGUI.createObstaclePane(), BorderLayout.CENTER);
        glass.setOpaque(false);
        glass.setVisible(true);
//		JLayeredPane layeredPane = new JLayeredPane();
//		layeredPane.setPreferredSize(boardSize);
//		mainFrame.getContentPane().add(layeredPane, BorderLayout.CENTER);
////		layeredPane.setLayout(new BorderLayout());
////		Container c = selkiesGUI.createContentPane(boardSize);
////		c.setPreferredSize(boardSize);
//		layeredPane.add(selkiesGUI.createContentPane(boardSize), JLayeredPane.DEFAULT_LAYER);
////		layeredPane.add(selkiesGUI.createObstaclePane(),  Integer.valueOf(JLayeredPane.PALETTE_LAYER-1),1);
//
////		contentPane.add(layeredPane, BorderLayout.CENTER);
//		mainFrame.setContentPane(contentPane);
        mainFrame.setMinimumSize(new Dimension(1020, 750));
        mainFrame.setPreferredSize(boardSize);

        // Display the window, setting the size
//		frame.setSize(900, 600);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // for center screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Creates and returns a JMenuBar for the Selkies application menu.
     *
     * @return The JMenuBar for the Selkies application.
     */
    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Selkies Menu");
        JMenuItem menuItem;

        menuBar.add(menu);

        // A group of JMenuItems. You can create other menu items here if desired
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a submenu
        menu.addSeparator();

        menuItem = new JMenuItem("Help");
        menuItem.addActionListener(this);

        menu.add(menuItem);
        return menuBar;
    }

    /**
     * Create and fill content pane
     *
     * @return {@link JPanel}
     */
    public Container createContentPane(Dimension preferredSize) {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(preferredSize);
        leftPanel.add(createGamePane(), BorderLayout.CENTER);
        infoPane = createInfoPane(new JPanel());
        rightPanel.add(infoPane, BorderLayout.CENTER);
        dicePane = createDicePane(new JPanel());
        rightPanel.add(dicePane, BorderLayout.SOUTH);
        contentPane.add(leftPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
        return contentPane;
    }

    /**
     * Swaps the image on a JButton with a dice image, waits for 2 seconds,
     * then displays the generated random number as text on the button.
     *
     * @param button The JButton to perform the image swap on.
     */
    private static void swapImage(JButton button) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Replace the image with the dog image
//				SwingUtilities.invokeLater(() -> button.setIcon(new ImageIcon(new ImageIcon(loadImage("dice.gif")))));

                // Wait for 2 seconds
                Thread.sleep(2000);

                SwingUtilities.invokeLater(() -> {
                    button.setIcon(null);
                    button.setPreferredSize(new Dimension(200, 200));
                    // Generate a random number
                    diceValue = generateRandomNumber(1, 6);
                    // Replace the image with the text containing the random number
                    button.setText("Move by " + diceValue + " steps");
                    System.out.println("dice value " + diceValue);
                });

                return null;
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    /**
     * function generate random number in closed range from MIN to MAX
     *
     * @param min - beginner value
     * @param max - ended value
     * @return int random number
     */
    public static int generateRandomNumber(int min, int max) {
//        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * function create sequentially range, with randomized shuffle
     * https://stackoverflow.com/a/8115744
     *
     * @param min
     * @param max
     * @return
     */
    public static List<Integer> generateListRandomNumber(int min, int max) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    /**
     * Create game pane.
     *
     * @return {@link JPanel}
     */
    public Container createGamePane() {
        // temporally create and fill fields with players, for check placement pictograms
//		List<Player> playerList = new ArrayList<>();
//		playerList.add(new Player(PaintUtils::drawTriangle));
////		playerList.add(new Player(PaintUtils::drawDiamond));
////		playerList.add(new Player(PaintUtils::drawCircle));
//		playerList.add(new Player(PaintUtils::drawSquare));
//		playerList.add(new Player(PaintUtils::drawStar));

        int numButtons = GRID_SIZE_X * GRID_SIZE_Y;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE_Y, GRID_SIZE_X));
        buttonArray = new JButtonField[numButtons];
        StringBuilder sb = new StringBuilder(400);
        int value;
        for (int i = numButtons - 1; i >= 0; i--) {
            value =
                    (
                            (i / 10 % 2) * i +
                                    ((i / 10 % 2 - 1) * -1 * (((i / 10) + 1) * 10 - (i % 10 + 1)))
                    );
            sb.append(value + 1).append("\t");
//			buttonArray[value] = new JButtonField("%d".formatted(value + 1), playerList);
            buttonArray[value] = new JButtonField(value);

            // This label is used to identify which button was clicked in the action listener
            buttonArray[value].setActionCommand("" + (value + 1)); // String "0", "1" etc.
            buttonArray[value].addActionListener(this);
            grid.add(buttonArray[value]);

            if (i % 10 == 0) {
                sb.append("\n");
            }
        }
        System.out.println(sb);
        return grid;
    }

    /**
     * Create info pane. Info for all players will be showing in this pane
     *
     * @return {@link JPanel}
     */
    public JComponent createInfoPane(JComponent info) {
        info.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        info.setLayout(new GridLayout(10, 0));
//		info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
//		Dimension d = new Dimension(240, 1);
//		info.add(new Box.Filler(d, d, d));
        PlayerInfoPane playerInfoPane = new PlayerInfoPane() {
            @Override
            public void showInfo() {
                showSerialNumber(" s/n ");
                showPlayerPictogram("Player Icon");
                showPlayerName("Player name");
                //https://stackoverflow.com/a/60546490
                showPlayerPosition(String.format(
                        "<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",
                        "Current position"
                ));
                showPlayerSteps(String.format(
                        "<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",
                        "All made steps"
                ));
            }
        };
        playerInfoPane.createGUI();
        playerInfoPane.showInfo();
        playerInfoPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(playerInfoPane);
        this.infoPane = info;
        return info;
    }

    /**
     * Creates a dice button with an initial dice image. When clicked, it either swaps
     * the image with an animated sequence or, if the image is already swapped, performs
     * the player's move and handles obstacles.
     *
     * @param dice The container for the dice button.
     * @return The created dice component.
     */
    public JComponent createDicePane(JComponent dice) {
        JButton diceButton = new JButton();
        diceButton.setIcon(loadImageGif("dice.png"));

        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (diceButton.getIcon() != null) {
                    diceButton.setIcon(loadImageGif("dice.gif"));
                    swapImage(diceButton);
                } else {
                    diceButton.setIcon(loadImageGif("dice.png"));
                    if (Objects.nonNull(playerList) && !playerList.isEmpty()) {
                        makeCurrentStep();
                        setNextPlayer();
                    }
                }
            }
        });

        dice.add(diceButton);

        return dice;
    }

    //https://www.rgagnon.com/javadetails/java-0240.html

    /**
     * Loads an image in GIF format from the specified file name.
     *
     * @param fileName The name of the GIF file to be loaded.
     * @return An ImageIcon object representing the loaded GIF image.
     */
    public ImageIcon loadImageGif(String fileName) {
        ImageIcon buff = null;
        URL url = this.getClass().getResource(fileName);
        return new ImageIcon(url);
    }

    /**
     * Moves the current player based on the dice value, updates the game pane, and checks for obstacles.
     * If the new position is beyond the last cell, it adjusts the position accordingly.
     * Displays a congratulatory message if the player reaches the last cell.
     */
    private void makeCurrentStep() {
        Player player = getCurrentPlayer();
        int currentPosition = player.getPlayerPos();
        int newPosition = currentPosition + diceValue;

        // Adjust the position if it exceeds the last cell
        if (newPosition > 99) {
            newPosition = 99 - (newPosition - 99);
        }

        // Move the player and update the panes
        buttonArray[currentPosition].removePlayer(player);
        buttonArray[newPosition].addPlayer(player);
        player.setPlayerPos(newPosition);
        checkAndInteractObstacle(player);
        updateGamePane();
        updateInfoPane();

        // Display a congratulatory message if the player reaches the last cell
        if (newPosition == 99) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    String.format("<html>Congratulations, %s! You are <b>W I N N E R!</b></html>", player.getPlayerName()),
                    "Congratulations!",
                    JOptionPane.INFORMATION_MESSAGE
            );
            NewGame();
        }
    }

    /**
     * Creates and returns an obstacle pane with specific styling.
     *
     * @return The created ObstaclePane.
     */
    public ObstaclePane createObstaclePane() {
        this.obstaclePane = new ObstaclePane();
        this.obstaclePane.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        this.obstaclePane.setOpaque(false);
        this.obstaclePane.setVisible(true);
        return this.obstaclePane;
    }

    /**
     * Adds a PlayerInfoPane for the specified player to the playerPaneList and infoPane.
     *
     * @param player The player for whom to add the information pane.
     */
    private void addInfoWidget(final Player player) {
        PlayerInfoPane playerInfoPane = new PlayerInfoPane();
        playerInfoPane.createGUI();
        playerInfoPane.setPlayer(player);
        playerInfoPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerPaneList.add(playerInfoPane);
        this.infoPane.add(playerInfoPane);
    }

    /**
     * This method handles events from the Menu and the board.
     */
    public void actionPerformed(ActionEvent e) {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent) (e.getSource());

        if (classname.equals("JMenuItem")) {
            JMenuItem menusource = (JMenuItem) (e.getSource());
            String menutext = menusource.getText();

            // Determine which menu option was chosen
            switch (menutext) {
                case "New Game":
                    NewGame();
                    break;
                case "Load Game":
                    LoadGame();
                    break;
                case "Save Game":
                    SaveGame();
                    break;
                case "Help":
                    Help();
                    break;
            }
        }
        // Handle the event from the user clicking on a command button
        else if (classname.equals("JButtonField")) {
            JButtonField button = (JButtonField) (e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand()) - 1;
            int row = bnum % GRID_SIZE_X;
            int col = bnum / GRID_SIZE_X;

            System.out.println("bnum=" + bnum);
            System.out.println("heght - %d, width - %d".formatted(button.getHeight(), button.getWidth()));

            /* SelkiesGui    Add your code here to handle user clicking on the grid ***********/
            clickSquare(row, col);
        }
    }

    /**
     * Returns the class name
     */
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    /**
     * Sets a given square in the grid (at [row][col]) to a specific character or colour
     */
    public boolean setGuiSquare(int row, int col, GRID_COLOUR colour) {
        int bnum = col * GRID_SIZE_X + row;
        if (bnum >= (GRID_SIZE_X * GRID_SIZE_Y)) {
            return false;
        } else {
            switch (colour) {
                case RED:
                    buttonArray[bnum].setBackground(Color.red);
                    break;
                case YELLOW:
                    buttonArray[bnum].setBackground(Color.yellow);
                    break;
                case BLANK:
                    buttonArray[bnum].setBackground(Color.gray);
                    break;
                default:
                    buttonArray[bnum].setBackground(Color.gray);
                    break;
            }
        }
        return true;
    }

    /**
     * Sets the next player as the currently selected player in a circular manner.
     * Updates the information pane accordingly.
     */
    public void setNextPlayer() {
        Player currentPlayer = getCurrentPlayer();
        int nextPlayerNumber = currentPlayer.getSerialNumber();

        // Reset to the first player if the current player is the last one
        if (nextPlayerNumber == playerList.size()) {
            nextPlayerNumber = 0;
        }

        currentPlayer.setSelected(false);
        playerMap.get(++nextPlayerNumber).setSelected(true);

        updateInfoPane();
    }

    /**
     * Checks for obstacles at the player's current position and performs interaction if needed.
     *
     * @param player The player to check for obstacles and interact accordingly.
     */
    private void checkAndInteractObstacle(Player player) {
        final int position = player.getPlayerPos();

        // Check each obstacle for interaction
        for (Obstacle obstacle : obstacleList) {
            if (obstacle.isNeedPerformance(position)) {
                // Move the player to the obstacle's end position
                buttonArray[position].removePlayer(player);
                buttonArray[obstacle.getEndButton().getPosition()].addPlayer(player);
                player.setPlayerPos(obstacle.getEndButton().getPosition());

                // Update the game and information panes
                updateGamePane();
                updateInfoPane();

                // Interaction completed, return to avoid further interactions
                return;
            }
        }
    }

    /**
     * Retrieves the currently selected player.
     *
     * @return The currently selected player or null if none is selected.
     */
    private Player getCurrentPlayer() {
        for (Player player : playerList) {
            if (player.isSelected()) {
                return player;
            }
        }
        return null;
    }

    /**
     * This method is called from the Menu event: New Game.
     * SelkiesGui
     */
    public void NewGame() {
        System.out.println("New game selected");

        // Initialise your game
        PopupAddPlayers popupAddPlayers = new PopupAddPlayers(mainFrame);
        popupAddPlayers.setVisible(true);
        List<Player> listNewPlayers = popupAddPlayers.getPlayers();
        if (listNewPlayers.isEmpty() || listNewPlayers.size() < 2) {
            System.out.println("Players doesn't entered");
            popupAddPlayers.dispose();
            return;
        } else {
            prepareNewGame();
            playerList.addAll(listNewPlayers);
            playerList.forEach(System.out::println);
        }
        popupAddPlayers.dispose();

        final List<Integer> orderList = SelkiesGui.generateListRandomNumber(1, playerList.size());
        AtomicInteger ai = new AtomicInteger(0);
        playerList.forEach(p -> {
            p.setSerialNumber(orderList.get(ai.getAndIncrement()));
            buttonArray[0].addPlayer(p);
            addInfoWidget(p);
        });
        playerMap = playerList.stream()
                .collect(Collectors.toMap(Player::getSerialNumber, Function.identity()));
        playerMap.get(1).setSelected(true);
        updateInfoPane();
//		playerPaneList.get(0).setSelectedPane(true);
        createObstacle();
        // Players are entered
        // v 1. clear game field
        // 2. recreate obstacles
        // v 3. create PlayerInfoPane for each player
        // 4. Start game

//		GameDataManager.askPlayerNoAndName();
    }

    /**
     * Prepares the game for a new round by resetting obstacles, buttons, and player-related components.
     */
    private void prepareNewGame() {
        obstaclePane.clearObstacle();
        obstaclePane.repaint();
        obstacleList.forEach(Obstacle::prepareNewGame);
        obstacleList.clear();
        Arrays.stream(buttonArray).forEach(JButtonField::prepareNewGame);
        playerPaneList.clear();
        this.infoPane.removeAll();
        this.infoPane.revalidate();
        this.infoPane.repaint();
        createInfoPane(this.infoPane);

        if (Objects.isNull(playerList)) {
            playerList = new ArrayList<>();
        } else if (!playerList.isEmpty()) {
            playerList.clear();
        }
        unique.clear();
    }

    /**
     * Creates obstacles for the game, alternating between Munro and Selkie obstacles.
     * Each obstacle is assigned a random pair of buttons as its start and end points.
     */
    public void createObstacle() {
        System.out.println("Creating obstacles...");
        Obstacle obstacle;
        Pair pair;
        for (int i = 0; i < 10; i++) {
            pair = getRandomPair();
            System.out.println("min = %d, max = %d".formatted(pair.vMin, pair.vMax));
            if (i % 2 == 0) {
                obstacle = new Munro();
                obstacle.setStartButton(buttonArray[pair.vMin]);
                obstacle.setEndButton(buttonArray[pair.vMax]);
            } else {
                obstacle = new Selkie();
                obstacle.setStartButton(buttonArray[pair.vMax]);
                obstacle.setEndButton(buttonArray[pair.vMin]);
            }
//			obstacle.setObstaclePane(obstaclePane);
            obstacleList.add(obstacle);
            obstaclePane.addObstacle(obstacle);
        }
    }

    /**
     * Updates the player information displayed on the info pane.
     * Iterates through each player pane, shows their updated information, and repaints the panes.
     * Introduces a small delay (30 milliseconds) to allow for visual updates.
     */
    public void updateInfoPane() {
        playerPaneList.forEach(pn -> {
            pn.showInfo();
            pn.repaint();
        });
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update game board
     */
    public void updateGamePane() {
        Arrays.stream(buttonArray).forEach(Component::repaint);
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * generate pair random numbers
     *
     * @return
     */
    private Pair getRandomPair() {
        Pair pair = new Pair();
//		buttonArray from 0 to 99, that why to range need from 1 to 98
        int v1 = generateUniqueValueForObstacle();
        int v2 = generateUniqueValueForObstacle();
        pair.vMax = Math.max(v1, v2);
        pair.vMin = Math.min(v1, v2);
        return pair;
    }

    private final HashSet<Integer> unique = new HashSet<>(50);

    private int generateUniqueValueForObstacle() {
        int temp;
        do {
            temp = SelkiesGui.generateRandomNumber(1, 98);
        } while (unique.contains(temp));
        unique.add(temp);
        return temp;
    }

    //************************************************************************
    //*** SelkiesGui: Modify the methods below to respond to Menu and Mouse click events

    /**
     * This method is called from the Menu event: Load Game.
     * SelkiesGui
     */
    public void LoadGame() {
        System.out.println("Load game selected");
        Save newLoad = Save.load();

        if (Objects.nonNull(newLoad)) {

            prepareNewGame();

            playerList.addAll(newLoad.getPlayerList());

            for (Player player : newLoad.getPlayerList()) {
                addInfoWidget(player);
                buttonArray[player.getPlayerPos()].addPlayer(player);
            }
            playerMap = playerList.stream()
                    .collect(Collectors.toMap(Player::getSerialNumber, Function.identity()));
            updateInfoPane();

            obstacleList.addAll(newLoad.getObstacleList());
            for (Obstacle obstacle : newLoad.getObstacleList()) {
                obstacle.setStartButton(buttonArray[obstacle.getStartPosition()]);
                obstacle.setEndButton(buttonArray[obstacle.getEndPosition()]);
                obstaclePane.addObstacle(obstacle);
            }
            updateGamePane();
        }
    }

    /**
     * This method is called from the Menu event: Save Game.
     * SelkiesGui
     */
    public void SaveGame() {
        System.out.println("Save game selected");

        Save save = new Save(playerList, obstacleList);
        save.save();
    }

    /**
     * Displays a help message.
     * Prints "Help is here!" to the console and shows a dialog with a helpful message.
     */
    public void Help() {
        System.out.println("Help is here!");
        mainFrame.setPreferredSize(new Dimension(100, 100));
        mainFrame.pack();

        JOptionPane.showMessageDialog(
                mainFrame,
                String.format("<html>Snake heads move players down, creating a disadvantage. No action on snake tails. Ladder bottoms move players up for an advantage. No action on ladder tops.</html>"),
                "HELP",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * This method is called from the Mouse Click event.
     * SelkiesGui
     */
    public void clickSquare(int row, int col) {
        System.out.println("Clicked square at (" + row + ", " + col + ")");

        // Set the square clicked on to be red
        setGuiSquare(row, col, GRID_COLOUR.YELLOW);
    }

    /**
     * Enumerated type to allow us to refer to RED, YELLOW or BLANK
     */
    private enum GRID_COLOUR {
        BLANK,
        YELLOW,
        RED
    }

    /**
     * A simple class representing a pair of integers, with {@code vMax} and {@code vMin} as public fields.
     */
    class Pair {
        public int vMax;
        public int vMin;
    }
}