/**
 * @Author _se.ho
 * @create 2023-11-21
 **/

public abstract class GameObject {

	private int posX;
	private int posY;

	/**
	 * Gets the X-coordinate of the object.
	 *
	 * @return The X-coordinate.
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Sets the X-coordinate of the object.
	 *
	 * @param posX The X-coordinate to be set.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Gets the Y-coordinate of the object.
	 *
	 * @return The Y-coordinate.
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Sets the Y-coordinate of the object.
	 *
	 * @param posY The Y-coordinate to be set.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Abstract method to interact with another GameObject.
	 *
	 * @param object The GameObject to interact with.
	 */
	abstract void interactWithObject(GameObject object);
}