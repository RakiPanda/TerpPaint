import javax.swing.*;
import java.awt.event.*;



/** Interface for all tools so that functionality can be abstracted.
 * @author TerpPaint
 * @version 2.0
 */
public interface ourTool {
    /** Called whenever the mouse is clicked.
     * @param mevt holds a MouseEvent
     * @param theCanvas holds a main_canvas
     */
    public void clickAction(MouseEvent mevt,main_canvas theCanvas);

    /** Called for all dragging actions.
     * @param mevt holds a MouseEvent for the dragging action
     * @param theCanvas holds a main_canvas
     */
    public void dragAction(MouseEvent mevt,main_canvas theCanvas);

	/** Called for all mouse releasing actions.
	 * @param mevt holds a MouseEvent
	 * @param theCanvas holds a main_canvas
	 */
    public void mouseReleaseAction(MouseEvent mevt,main_canvas theCanvas);
	// draws the shape on shapes
}

