import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public class Pictogram extends JLabel {

	private Painter painter = null;
//	private BiConsumer<Point, Graphics> painter = null;

	/**
	 * Constructs a default Pictogram with a blank label.
	 */
	public Pictogram() {
		super("   ", null, CENTER);
	}

	/**
	 * Constructs a Pictogram with a specified painter.
	 *
	 * @param painter The painter for the Pictogram.
	 */
	public Pictogram(final Painter painter) {
		super("", null, CENTER);
		this.painter = painter;
	}

	/**
	 * Overrides the paintComponent method to paint the Pictogram.
	 *
	 * @param g The Graphics object to paint on.
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (Objects.nonNull(painter)) {
			painter.get().accept(new Point(getWidth() / 2, getHeight() / 2), g);
		}
	}

	/**
	 * Overrides the getMinimumSize method to set a minimum size for the Pictogram.
	 *
	 * @return The minimum size of the Pictogram.
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(20, 16);
	}

	/**
	 * Sets the painter for the Pictogram and triggers a repaint.
	 *
	 * @param painter The painter to be set.
	 */
	public void setPictogramPainter(final Painter painter) {
		this.painter = painter;
		this.repaint();
	}

	/**
	 * Gets an instance of Pictogram based on the specified type.
	 *
	 * @param typePictogram The type of Pictogram.
	 * @return The corresponding Pictogram instance.
	 */
	public static Pictogram getInstance(final Integer typePictogram) {
		if (Objects.equals(typePictogram, PaintUtils.STAR)) {
			return new Pictogram(Painter.STAR);
		}
		if (Objects.equals(typePictogram, PaintUtils.SQUARE)) {
			return new Pictogram(Painter.SQUARE);
		}
		if (Objects.equals(typePictogram, PaintUtils.TRIANGLE)) {
			return new Pictogram(Painter.TRIANGLE);
		}
		if (Objects.equals(typePictogram, PaintUtils.DIAMOND)) {
			return new Pictogram(Painter.DIAMOND);
		}
		return new Pictogram(Painter.CIRCLE);
	}
}