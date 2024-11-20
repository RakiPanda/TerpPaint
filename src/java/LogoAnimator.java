
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;
import java.awt.Toolkit.*;
import java.awt.image.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import java.io.*;
import java.awt.geom.*;

/** The class LogoAnimator displays a series of images to create animation.
 *
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 */
public class LogoAnimator extends JPanel implements ActionListener {

	/** series of layers to be displayed as part of the animation.
	 */
	protected ImageIcon images[];

	/** number of images.
	 */
	private int totalImages;

	/** current image index.
	 */
	private int currentImage = 0;

	/** millisecond delay.
	 */
	private int animationDelay;

	/** image width.
	 */
	private int width;

	/** image height.
	 */
	private int height;

	/** Timer drives animation.
	 */
	private Timer animationTimer;

	// Ronald 5.2
	/** counter.
	 **/
	private int counter;

	/** cycle.
	 **/
	private int cycle;

	/** image container.
	 */
	private Container container;

	/** transition type.
	 */
	public String transition;

	/** initialize LogoAnimator
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 */
	public LogoAnimator() {
	}

	/** run animation by first loading LinkedList of images
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 * @param frame   a Frame object
	 * @param layerList linked list of images
	 * @param transition    a String representing transition type
	 * @param milliseconds transition interval
	 * @param CycleNumber   an int specifying the cycle number
	 */
	// Ronald 5.2 starts
	public void runAnimation(java.awt.Frame frame,
			java.util.LinkedList layerList, int milliseconds,
			java.lang.String transition, int CycleNumber) {
		JDialog window = new JDialog(frame, false);
		//window.setVisible(false);//Ronald phase 5
		window.setTitle("Please wait");//Ronald phase 5
		window.setSize(250, 100);
		window.setLocation(400, 300);
		window.setVisible(true); // display window
		this.transition = transition;
		if (transition.compareTo("(none)") == 0) {
			animationDelay = (int) milliseconds; // 50 stages of transition between each layer
			totalImages = layerList.size();
			images = new ImageIcon[totalImages];
			// load images
			for (int count = 0; count < images.length; ++count) {
				images[count] = new ImageIcon(
						(java.awt.image.BufferedImage) layerList.get(count));
			}
		}
		if (transition.compareTo("Blur") == 0) { // Ronald 5.2
			animationDelay = (int) ((double) milliseconds / 50); // 50 stages of transition between each layer
			BufferedImage tempImage;
			int tempSize;
			java.util.LinkedList transImage = new java.util.LinkedList();
			// transition images for one cycle
			totalImages = 5 + ((layerList.size() - 1) * (22 + 5 + 23))
					+ (22 + 23);  // this is 50 + (the number of layers in your image) * 50 
			for (int i = 0; i < 5; i++) {
				transImage.add((java.awt.image.BufferedImage) layerList.get(0));
				// update percent done
				window.setTitle("Please wait....... "
						+ ((transImage.size() * 100) / totalImages) + "% done");
			}
			for (int i = 0; i < layerList.size() - 1; i++) {
				tempImage = (java.awt.image.BufferedImage) layerList.get(i);
				for (int j = 0; j < 22; j++) {
					tempImage = doTransition(tempImage);
					transImage.add(tempImage); // add the blur transitions from clear to blur
					// update percent done
					window.setTitle("Please wait....... "
							+ ((transImage.size() * 100) / totalImages)
							+ "% done");
				}
				tempSize = transImage.size();
				for (int j = 0; j < 5; j++) {
					transImage.add((java.awt.image.BufferedImage) layerList
							.get(i + 1));
					// update percent done
					window.setTitle("Please wait....... "
							+ ((transImage.size() * 100) / totalImages)
							+ "% done");
				}
				tempImage = (java.awt.image.BufferedImage) layerList.get(i + 1);
				for (int j = 0; j < 23; j++) {
					tempImage = doTransition(tempImage);
					try {
						Thread.sleep(5);
					}
					catch (Exception e) {e.printStackTrace();};
					transImage.add(tempSize, tempImage); // add the blur transitions from blur to clear
					// update percent done
					window.setTitle("Please wait....... "
							+ ((transImage.size() * 100) / totalImages)
							+ "% done");
				}
			}
			// complete the cycle by doing the transitions between last and first
			tempImage = (BufferedImage) layerList.get(layerList.size() - 1); // last image
			for (int i = 0; i < 22; i++) {
				tempImage = doTransition(tempImage);
				transImage.add(tempImage); // add the blur transitions from clear to blur
				// update percent done
				window.setTitle("Please wait....... "
						+ ((transImage.size() * 100) / totalImages) + "% done");
			}
			tempSize = transImage.size();
			tempImage = (java.awt.image.BufferedImage) layerList.get(0); // first image
			for (int i = 0; i < 23; i++) {
				tempImage = doTransition(tempImage);
				transImage.add(tempSize, tempImage); // add the blur transitions from blur to clear
				// update percent done
				window.setTitle("Please wait....... "
						+ ((transImage.size() * 100) / totalImages) + "% done");
			}

			// load all transition images into array for processing animation
			images = new ImageIcon[totalImages];
			// load images
			for (int count = 0; count < images.length; ++count) {
				images[count] = new ImageIcon(
						(java.awt.image.BufferedImage) transImage.get(count));
			}
			tempImage.flush();
		}
		// find the max height and width
		width = height = 0;
		for (int i = 0; i < layerList.size(); i++) {
			if (((BufferedImage) layerList.get(i)).getHeight() > height)
				height = ((BufferedImage) layerList.get(i)).getHeight();
			if (((BufferedImage) layerList.get(i)).getWidth() > width)
				width = ((BufferedImage) layerList.get(i)).getWidth();
		}
		window.setTitle("Animator");
		container = window.getContentPane();
		container.add(this);
		window.pack(); // make window just large enough for its GUI
		window.setVisible(true); // display window
		cycle = CycleNumber;
		counter = totalImages * (CycleNumber + 1);
		startAnimation();
	}

	// Ronald 5.2 ends

	/** display current image
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 * @param g graphic
	 */

	public void paintComponent(Graphics g) {
		if (transition.compareTo("Blur") == 0) {
			if (counter > (22 + 23) || cycle < 0) {
				super.paintComponent(g);
				images[currentImage].paintIcon(this, g, 0, 0);
				// move to next image only if timer is running
				if (animationTimer.isRunning()) {
					currentImage = (currentImage + 1) % totalImages;
					counter--;
				}
			} else {
				//	   super.paintComponent( g );
				//	   images[ 0 ].paintIcon( this, g, 0, 0 );
				stopAnimation();
			}
		} else {//(transition.compareTo("none")==0)
			if (counter > 0 || cycle < 0) {
				super.paintComponent(g);
				images[currentImage].paintIcon(this, g, 0, 0);
				// move to next image only if timer is running
				if (animationTimer.isRunning()) {
					currentImage = (currentImage + 1) % totalImages;
					counter--;
				}
			} else {
				stopAnimation();
			}
		}
	}

	/** respond to Timer's event
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 * @param actionEvent action events
	 */

	public void actionPerformed(ActionEvent actionEvent) {
		repaint(); // repaint animator
	}

	/** start or restart animation
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 */
	public void startAnimation() { // Ronald 5.2 starts
		if (animationTimer == null) {
			currentImage = 0;
			animationTimer = new Timer(animationDelay, this);
			animationTimer.start();
		} else {// continue from last image displayed
			if (!animationTimer.isRunning())
				animationTimer.restart();
		}
	}// Ronald 5.2 ends

	/** stop animation timer
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 */
	public void stopAnimation() {
		animationTimer.stop();
	}

	/** return minimum size of animation
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/** return preferred dimension of animation
	 *
	 * There are no OS/Hardware dependencies and no variances.	There is no need for any
	 * security constraints and no references to external specifications.
	 *
	 */
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	/** This method blurs the image to create transition effect.
	 * @param transImage The Buffered Image
	 * @return transImage The Buffered Image after it has been blurred for transition
	 */
	BufferedImage doTransition(BufferedImage transImage) {
		Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f,
				1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
		BufferedImageOp op = new ConvolveOp(kernel);

		for (int i = 0; i < 4; i++)
			transImage = op.filter(transImage, null);

		Graphics2D gd = transImage.createGraphics();

		gd.drawImage(transImage, 0, 0, null);
		return transImage;
	}
}