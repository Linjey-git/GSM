import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @Author _se.ho
 * @create 2023-11-19
 **/
public enum Painter implements Supplier<BiConsumer<Point, Graphics>> {

	//	case 0 /* STAR */ -> PaintUtils::drawStar;
	//	case 1 /* SQUARE */ -> PaintUtils::drawSquare;
	//	case 3 /* TRIANGLE */ -> PaintUtils::drawTriangle;
	//	case 4 /* DIAMOND */ -> PaintUtils::drawDiamond;
	//	default /* 2 - CIRCLE */ -> PaintUtils::drawCircle;
	STAR(PaintUtils.STAR),
	SQUARE(PaintUtils.SQUARE),
	CIRCLE(PaintUtils.CIRCLE),
	TRIANGLE(PaintUtils.TRIANGLE),
	DIAMOND(PaintUtils.DIAMOND);

	private final int numberPainter;

	/**
	 * Constructs a Painter with a specified painter number.
	 *
	 * @param numberPainter The painter number.
	 */
	Painter(final int numberPainter) {
		this.numberPainter = numberPainter;
	}

	/**
	 * Gets the BiConsumer for painting.
	 *
	 * @return The BiConsumer for painting.
	 */
	@Override
	public BiConsumer<Point, Graphics> get() {
		return PaintUtils.getConsumer(numberPainter);
	}

	/**
	 * Gets a Painter by index.
	 *
	 * @param index The index to get the Painter.
	 * @return The corresponding Painter.
	 */
	public static Painter getPainterByIndex(int index) {
		Painter[] arr = Painter.values();
		if (index < 0 || index >= arr.length) {
			return CIRCLE;
		}
		return arr[index];
	}
}