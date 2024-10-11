import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * @Author _se.ho
 * @create 2023-11-21
 **/

public class Player extends GameObject implements Serializable {


    private static final long serialVersionUID = 1L;
    private int playerPos = 0;

    /**
     * countSteps - variable counted steps during game
     */
    private int countSteps = 0;
    /**
     * name of player
     */
    private String playerName;


    private Painter painter;
//    private BiConsumer<Point, Graphics> painter;

    /**
     * Serial number in Game
     */
    private Integer serialNumber;

    private boolean isSelected;
    /**
     * Constructs a Player with a specified painter.
     *
     * @param painter The painter for the Player's pictogram.
     */
    public Player(final Painter painter) {
        this.painter = painter;
    }

    /**
     * Moves the player by the specified number of steps.
     *
     * @param steps The number of steps to move.
     */
    public void move(int steps) {
        setPlayerPos(getPlayerPos() + steps);
    }

    /**
     * Sets the default position and step count for the player.
     */
    public void setDefaultPos() {
        this.playerPos = 0;
        this.countSteps = 0;
    }

    /**
     * Checks if the player is selected.
     *
     * @return True if the player is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Sets the selected status of the player.
     *
     * @param selected True to select the player, false to deselect.
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Gets the current position of the player.
     *
     * @return The player's position.
     */
    public int getPlayerPos() {
        return playerPos;
    }

    /**
     * Sets the position of the player and increments the step count.
     *
     * @param playerPos The new position of the player.
     */
    public void setPlayerPos(int playerPos) {
        this.playerPos = playerPos;
        this.countSteps++;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player.
     *
     * @param playerName The new name for the player.
     */
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the count of steps taken by the player.
     *
     * @return The count of steps taken.
     */
    public int getCountSteps() {
        return countSteps;
    }

    /**
     * Gets the painter associated with the player's pictogram.
     *
     * @return The painter for the player's pictogram.
     */
    public Painter getPictogramPainter() {
        return painter;
    }

    /**
     * Paints the player's pictogram at the specified point.
     *
     * @param point The point at which to paint the pictogram.
     * @param g     The Graphics object for painting.
     */
    public void paint(Point point, Graphics g) {
        painter.get().accept(point, g);
    }

    /**
     * Gets the serial number of the player.
     *
     * @return The serial number of the player.
     */
    public Integer getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number of the player.
     *
     * @param serialNumber The new serial number for the player.
     */
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Overrides the interactWithObject method to handle interactions with other GameObjects.
     *
     * @param object The GameObject to interact with.
     */
    @Override
    void interactWithObject(GameObject object) {
        // Implementation of player interaction with other objects
    }
}