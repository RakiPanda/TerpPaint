/*
 * canvas_size.java
 *
 * Created on March 24, 2003, 3:24 PM
 */

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * This class set the width and height in pixels, cm, and inch for the canvas.
 * This function also converts the units from one form to another. There are two
 * states which are disable and enable. The OS is windows. No there are no
 * implementation variances. There are no security constraints. no external
 * secification.
 */

public class canvas_size extends javax.swing.JDialog {

	/**
	 * int value to indicate inches. cm or pixels.
	 */
	public int CHOICE;

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * INCH
	 */
	/**
	 * takes in an INT from the screen resolution.
	 */
	public static final int INCH = Toolkit.getDefaultToolkit()
			.getScreenResolution();

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * im
	 */
	/**
	 * is a bufferedImage variable.
	 */

	public BufferedImage im;

	/*
	 * If inches: lastSelected = 1 If cm: lastSelected = 2 If pixels:
	 * lastSelected = 3
	 */

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * lastedSelected
	 */
	/**
	 * the last CHOICE value that was used.
	 */

	public int lastSelected = 3;

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * w
	 */
	/**
	 * String of numbers for width.
	 */

	public String w = new String();

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * h
	 */
	/**
	 * String of numbers for height.
	 */

	public String h = new String();

	/**
	 * javax.swing.JDialog
	 */
	/**
	 * Assigns the width in pixels.
	 */

	public double pixelWidth = 1;

	/**
	 * Assigns the height in pixels.
	 */
	public double pixelHeight = 1;

	/**
	 * Assigns the width in inches.
	 */
	public double inchWidth;

	/**
	 * Assigns the height in inches.
	 */
	public double inchHeight;

	/**
	 * Assigns the width in cms.
	 */
	public double cmWidth;

	/**
	 * Assigns the height in cms.
	 */
	public double cmHeight;

	/**
	 * main_canvas.
	 */
	public main_canvas p;

	/**
	 * constructor, shows dialog box for user input of canvas size in units of
	 * their choice Paremeter can be either true or false. Set modal to true. NO
	 * return values. No NULL values. OS is windows. No implementation variances
	 * exception of boolean value is given anything other than boolean values
	 * 
	 * @param modal
	 *            is a boolean
	 * @param parent
	 *            is a java.awt.Frame variable
	 */

	public canvas_size(javax.swing.JFrame parent, boolean modal) {
		super(parent, modal);

		initComponents();

		/** converting to inches and cm */
		im = ((TerpPaint) this.getParent()).center.getBufferedImage();
		pixelWidth = im.getWidth();
		pixelHeight = im.getHeight();

		cmWidth = Math
				.round((pixelWidth / ((double) INCH * (double) 2.54)) * 100) / 100.0;
		cmHeight = Math
				.round((pixelHeight / ((double) INCH * (double) 2.54)) * 100) / 100.0;
		inchWidth = Math.round((pixelWidth / (double) INCH) * 100) / 100.0;
		inchHeight = Math.round((pixelHeight / (double) INCH) * 100) / 100.0;

		//	  w = w.valueOf(pixelWidth);
		//	  h = h.valueOf(pixelHeight);

//		setTitle("New");
		setTitle("新規");
		im.flush();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * There is no state of transition There are no arguments No NULL values. No
	 * return values. Basically initializing. OS is windows. No implementation
	 * variances. No exceptions. NO security constraints.
	 */

	public void initComponents() {

		/**
		 * javax.swing.ButtonGroup
		 */
		/**
		 * unitsButtons
		 */
		/**
		 * This is a ButtonGroup variable
		 */

		unitsButtons = new javax.swing.ButtonGroup();

		/**
		 * javax.swing.ButtonGroup
		 */
		/**
		 * colors
		 */
		/**
		 * This is a ButtonGroup variable
		 */

		colors = new javax.swing.ButtonGroup();

		/**
		 * javax.swing.JPanel
		 */
		/**
		 * okCancel
		 */
		/**
		 * This is a JPanel variable
		 */

		okCancel = new javax.swing.JPanel();

		/**
		 * javax.swing.ButtonGroup
		 */
		/**
		 * ok
		 */
		/**
		 * This is a JButton variable
		 */

		ok = new javax.swing.JButton();

		/**
		 * javax.swing.JButton
		 */
		/**
		 * cancel
		 */
		/**
		 * This is a JButton variable
		 */

		cancel = new javax.swing.JButton();

		/**
		 * javax.swing.JPanel
		 */
		/**
		 * buttonsText
		 */
		/**
		 * This is a JPanel variable
		 */

		buttonsText = new javax.swing.JPanel();

		/**
		 * javax.swing.JPanel
		 */
		/**
		 * units
		 */
		/**
		 * This is a JPanel variable
		 */

		units = new javax.swing.JPanel();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * inches
		 */
		/**
		 * This is a JRadioButton variable
		 */

		inches = new javax.swing.JRadioButton();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * cm
		 */
		/**
		 * This is a JRadioButton variable
		 */

		cm = new javax.swing.JRadioButton();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * pixels
		 */
		/**
		 * This is a JRadioButton variable
		 */

		pixels = new javax.swing.JRadioButton();

		/**
		 * javax.swing.JPanel
		 */
		/**
		 * widthHeight
		 */
		/**
		 * This is a JPanel variable
		 */

		widthHeight = new javax.swing.JPanel();

		/**
		 * javax.swing.JTextField
		 */
		/**
		 * widthText
		 */
		/**
		 * This is a JTextField variable
		 */

		widthText = new javax.swing.JTextField();

		/**
		 * javax.swing.JTextField
		 */
		/**
		 * heightText
		 */
		/**
		 * This is a JTextField variable
		 */

		heightText = new javax.swing.JTextField();

		/**
		 * javax.swing.JLabel
		 */
		/**
		 * Height
		 */
		/**
		 * This is a JLabel variable
		 */

		Height = new javax.swing.JLabel();

		/**
		 * javax.swing.JLabel
		 */
		/**
		 * Width
		 */
		/**
		 * This is a JLabel variable
		 */

		Width = new javax.swing.JLabel();

		/**
		 * javax.swing.JLabel
		 */
		/**
		 * color
		 */
		/**
		 * This is a JLabel variable
		 */

		color = new javax.swing.JPanel();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * colorScale
		 */
		/**
		 * This is a JRadioButton variable
		 */

		colorScale = new javax.swing.JRadioButton();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * blackwhite
		 */
		/**
		 * This is a JRadioButton variable
		 */

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		okCancel.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints1;

		ok.setText("OK");
		ok.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okActionPerformed(evt);
			}
		});

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		okCancel.add(ok, gridBagConstraints1);

//		cancel.setText("CANCEL");
		cancel.setText("キャンセル");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		okCancel.add(cancel, gridBagConstraints1);

		getContentPane().add(okCancel, java.awt.BorderLayout.EAST);

		buttonsText.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints2;

		units.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints3;

//		units.setBorder(new javax.swing.border.TitledBorder("Units"));
		units.setBorder(new javax.swing.border.TitledBorder("単位"));
//		inches.setText("Inches");
		inches.setText("インチ");
		unitsButtons.add(inches);
		inches.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				inchesActionPerformed(evt);
			}
		});

		gridBagConstraints3 = new java.awt.GridBagConstraints();
		gridBagConstraints3.insets = new java.awt.Insets(0, 0, 0, 10);
		units.add(inches, gridBagConstraints3);

//		cm.setText("Cm");
		cm.setText("cm");
		unitsButtons.add(cm);
		cm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmActionPerformed(evt);
			}
		});

		gridBagConstraints3 = new java.awt.GridBagConstraints();
		gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 10);
		units.add(cm, gridBagConstraints3);

		pixels.setSelected(true);
//		pixels.setText("Pixels");
		pixels.setText("ピクセル");
		unitsButtons.add(pixels);
		pixels.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pixelsActionPerformed(evt);
			}
		});

		gridBagConstraints3 = new java.awt.GridBagConstraints();
		gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);
		units.add(pixels, gridBagConstraints3);

		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.insets = new java.awt.Insets(20, 0, 0, 0);
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		buttonsText.add(units, gridBagConstraints2);

		widthHeight.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints4;

		widthText.setColumns(5);
		widthText.setMaximumSize(new java.awt.Dimension(4, 20));

		if (((TerpPaint) this.getParent()).nonEmptyClipboard)
			widthText.setText(Integer
					.toString(((TerpPaint) this.getParent()).clipBoardWidth));
		else
			widthText.setText(Integer.toString(new Double(((TerpPaint) this
					.getParent()).getSize().getWidth() - 63).intValue()));

		widthText.selectAll();
		gridBagConstraints4 = new java.awt.GridBagConstraints();
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.gridy = 0;
		widthHeight.add(widthText, gridBagConstraints4);

		heightText.setColumns(5);
		heightText.setMaximumSize(new java.awt.Dimension(4, 20));

		if (((TerpPaint) this.getParent()).nonEmptyClipboard)
			heightText.setText(Integer
					.toString(((TerpPaint) this.getParent()).clipBoardHeight));
		else
			heightText.setText(Integer.toString(new Double(((TerpPaint) this
					.getParent()).getSize().getHeight() - 85).intValue()));

		heightText.selectAll();
		gridBagConstraints4 = new java.awt.GridBagConstraints();
		gridBagConstraints4.gridx = 4;
		gridBagConstraints4.gridy = 0;
		widthHeight.add(heightText, gridBagConstraints4);

//		Height.setText("Height");
		Height.setText("高さ");
		gridBagConstraints4 = new java.awt.GridBagConstraints();
		gridBagConstraints4.gridx = 3;
		gridBagConstraints4.gridy = 0;
		gridBagConstraints4.insets = new java.awt.Insets(0, 30, 0, 0);
		widthHeight.add(Height, gridBagConstraints4);

//		Width.setText("Width");
		Width.setText("幅");
		gridBagConstraints4 = new java.awt.GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 0;
		widthHeight.add(Width, gridBagConstraints4);

		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		buttonsText.add(widthHeight, gridBagConstraints2);

		getContentPane().add(buttonsText, java.awt.BorderLayout.WEST);

		pack();
	}

	/**
	 * This method performs the action by the user, and resizes cnavas There is
	 * no state of transition The argument is an ActionEvent variable. No NULL
	 * values. No return values. Basically initializing. OS is windows. No
	 * implementation variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            ActionEvent
	 */

	public void okActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		//If the Width and Height aren't the same as they started out, then
		//you need to crop the image.

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * currWidth
		 */
		/**
		 * This is a double variable
		 */

		double currWidth = Double.parseDouble(widthText.getText());

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * currHeight
		 */
		/**
		 * This is a double variable
		 */

		double currHeight = Double.parseDouble(heightText.getText());

		System.out.println("currW " + currWidth + " currHei " + currHeight);

		pixelWidth = currWidth;
		pixelHeight = currHeight;

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * x1
		 */
		/**
		 * This is a double variable
		 */

		double x1 = im.getMinX();

		/**
		 * javax.swing.JRadioButton
		 */
		/**
		 * y1
		 */
		/**
		 * This is a double variable
		 */

		double y1 = im.getMinY();

		if (lastSelected == 1) {
			//inches
			//	      double newPixW = Math.round((currWidth*(double)INCH) *
			// 100)/100.0;
			//	      double newPixH = Math.round((currHeight*(double)INCH) *
			// 100)/100.0;

			double newPixW = currWidth * 72;
			double newPixH = currHeight * 72;

			((TerpPaint) this.getParent()).center.resizeImage((int) newPixW,
					(int) newPixH);

			//	      ((TerpPaint)this.getParent()).center.resizeImage((int)newPixW,
			// (int)newPixH );
			//		  ((TerpPaint)this.getParent()).center = new main_canvas(
			// (int)newPixW, (int)newPixH );
		}
		if (lastSelected == 2) {
			//cm
			//	      double newPixW = Math.round((currWidth * (double)INCH) *
			// (double)2.54 * 100)/100.0;
			//	      double newPixH = Math.round((currHeight * (double)INCH) *
			// (double)2.54 * 100)/100.0;

			double newPixW = currWidth * 72 / 2.54;
			double newPixH = currHeight * 72 / 2.54;

			((TerpPaint) this.getParent()).center.resizeImage((int) newPixW,
					(int) newPixH);
			//		  ((TerpPaint)this.getParent()).center = new main_canvas(
			// (int)newPixW, (int)newPixH );

		}
		if (lastSelected == 3) {
			//pixels
			((TerpPaint) this.getParent()).center.resizeImage((int) currWidth,
					(int) currHeight);
			//		  ((TerpPaint)this.getParent()).center = new main_canvas(
			// (int)currWidth, (int)currHeight );

		}

		((TerpPaint) this.getParent()).center.repaint();
		//Close the window when you're done

		CHOICE = choice2.OK;
		//this.closeDialog(new java.awt.event.WindowEvent(this, 0));
		setVisible(false);
		dispose();
	}

	/**
	 * shows values in the textfields in pixel units There is no state of
	 * transition The argument is an ActionEvent variable. No NULL values. No
	 * return values. Basically initializing. OS is windows. No implementation
	 * variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            ActionEvent
	 */

	public void pixelsActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		//change widthText from cm or inches to pixels

		if (lastSelected == 1) {
			inchWidth = Double.parseDouble(widthText.getText());
			inchHeight = Double.parseDouble(heightText.getText());
			inchWidth = Math.round((inchWidth * 72) * 100 / 100.0);
			inchHeight = Math.round((inchHeight * 72) * 100 / 100.0);

			w = w.valueOf(inchWidth);
			h = h.valueOf(inchHeight);

			//	  w = w.valueOf( Math.round( ( inchWidth /(double)INCH ) *
			// 100)/100.0
			//);
			//	  h = h.valueOf( Math.round( ( inchHeight /(double)INCH) *
			// 100)/100.0
			//);

			widthText.setText(w);
			heightText.setText(h);
		}
		if (lastSelected == 2) {
			cmWidth = Double.parseDouble(widthText.getText());
			cmHeight = Double.parseDouble(heightText.getText());

			cmWidth = Math.round(((cmWidth * 72) * 100 / 2.54) / 100.0);
			cmHeight = Math.round(((cmHeight * 72) * 100 / 2.54) / 100.0);

			w = w.valueOf(cmWidth);
			h = h.valueOf(cmHeight);

			//
			//w = w.valueOf(
			//				Math.round(( cmWidth /((double)INCH *
			//							(double)2.54))*100)/100.0 );
			//h = h.valueOf(
			//				Math.round(( cmHeight /((double)INCH *
			//							(double)2.54))*100)/100.0 );

			widthText.setText(w);
			heightText.setText(h);

		}

		lastSelected = 3;
	}

	/**
	 * converts the values in th textboxes to cm units There is no state of
	 * transition The argument is an ActionEvent variable. No NULL values. No
	 * return values. Basically initializing. OS is windows. No implementation
	 * variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            ActionEvent
	 */

	public void cmActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		//change widthText from pixels or inches to centimeters
		if (lastSelected == 1) {
			//converting from inches
			inchWidth = Double.parseDouble(widthText.getText());
			inchHeight = Double.parseDouble(heightText.getText());

			/**
			 * javax.swing.JRadioButton
			 */
			/**
			 * width
			 */
			/**
			 * This is a double variable
			 */

			double width = Double.parseDouble(widthText.getText());

			/**
			 * javax.swing.JRadioButton
			 */
			/**
			 * height
			 */
			/**
			 * This is a double variable
			 */

			double height = Double.parseDouble(heightText.getText());

			width = width * (double) 2.54;
			height = height * (double) 2.54;

			width = Math.round(width * 100) / 100.0;
			height = Math.round(height * 100) / 100.0;

			w = w.valueOf(width);
			h = h.valueOf(height);

			//after we've calculated the values, we finally set the text
			widthText.setText(w);
			heightText.setText(h);
		}
		if (lastSelected == 3) {
			//converting from pixels
			pixelHeight = Double.parseDouble(heightText.getText());
			pixelWidth = Double.parseDouble(widthText.getText());

			pixelWidth = Math.round(((double) pixelWidth) * 2.54 / (72) * 100) / 100.0;
			pixelHeight = Math
					.round(((double) pixelHeight) * 2.54 / (72) * 100) / 100.0;

			w = w.valueOf(pixelWidth);
			h = h.valueOf(pixelHeight);

			//	      w = w.valueOf(cmWidth);
			//	      h = h.valueOf(cmHeight);

			//after we've calculated the values, we finally set the text
			widthText.setText(w);
			heightText.setText(h);
		}

		lastSelected = 2;
	}

	/**
	 * converts the values in the textboxes into inches units There is no state
	 * of transition The argument is an ActionEvent variable. No NULL values. No
	 * return values. Basically initializing. OS is windows. No implementation
	 * variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            ActionEvent
	 */

	public void inchesActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		//change widthText from pixels or centimeters to inches
		if (lastSelected == 2) {
			//converting from cm
			cmWidth = Double.parseDouble(widthText.getText());
			cmHeight = Double.parseDouble(heightText.getText());

			/**
			 * javax.swing.JRadioButton
			 */
			/**
			 * width
			 */
			/**
			 * This is a double variable
			 */

			double width = Double.parseDouble(widthText.getText());

			/**
			 * javax.swing.JRadioButton
			 */
			/**
			 * height
			 */
			/**
			 * This is a double variable
			 */

			double height = Double.parseDouble(heightText.getText());

			width = width / (double) 2.54;
			height = height / (double) 2.54;

			width = Math.round(width * 100) / 100.0;
			height = Math.round(height * 100) / 100.0;

			w = w.valueOf(width);
			h = h.valueOf(height);

			//after we've calculated the values, we finally set the text
			widthText.setText(w);
			heightText.setText(h);
		}
		if (lastSelected == 3) {

			pixelHeight = Double.parseDouble(heightText.getText());
			pixelWidth = Double.parseDouble(widthText.getText());

			pixelWidth = Math.round((pixelWidth / 72) * 100) / 100.0;
			pixelHeight = Math.round((pixelHeight / 72) * 100) / 100.0;

			w = w.valueOf(pixelWidth);
			h = h.valueOf(pixelHeight);

			//after we've calculated the values, we finally set the text
			widthText.setText(w);
			heightText.setText(h);
		}

		lastSelected = 1;
	}

	/**
	 * cancels and closes the dialog box, without changing the size There is no
	 * state of transition The argument is an ActionEvent variable. No NULL
	 * values. No return values. Basically initializing. OS is windows. No
	 * implementation variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            ActionEvent
	 */

	public void cancelActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		CHOICE = choice2.CANCEL;
		this.closeDialog(new java.awt.event.WindowEvent(this, 0));
		// setVisible(false);

		//  dispose();
	}

	/**
	 * closes the dialog box without changing the size There is no state of
	 * transition The argument is an ActionEvent variable. No NULL values. No
	 * return values. Basically initializing. OS is windows. No implementation
	 * variances. No exceptions. NO security constraints.
	 * 
	 * @param evt
	 *            WindowEvent
	 */

	public void closeDialog(java.awt.event.WindowEvent evt) {
		setVisible(false);
		dispose();
	}

	/**
	 * This is the method which creates a new cnavas_size, never called Set the
	 * args variable with strings. Variable can be set to NULL. No return value.
	 * No algorithm defined. OS is windows. No implementation variances
	 * exception would be thrown if there is a number assign to the parameter.
	 * No security constraint
	 * 
	 * @param args
	 *            the command line arguments
	 */

	public static void main(String args[]) {
		new canvas_size(new javax.swing.JFrame(), true).show();
	}

	// Variables declaration - do not modify

	/**
	 * javax.swing.ButtonGroup
	 */
	/**
	 * unitsButtons
	 */
	/**
	 * This is a ButtonGroup variable holds units radio buttons.
	 */

	private javax.swing.ButtonGroup unitsButtons;

	/**
	 * javax.swing.ButtonGroup
	 */
	/**
	 * colors
	 */
	/**
	 * This is a ButtonGroup variable.
	 */

	private javax.swing.ButtonGroup colors;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * colorScale
	 */
	/**
	 * This is a JRadioButton variable holds ok and cancel jbuttons.
	 */

	private javax.swing.JPanel okCancel;

	/**
	 * javax.swing.ButtonGroup
	 */
	/**
	 * ok
	 */
	/**
	 * This is a JButton variable ok.
	 */

	private javax.swing.JButton ok;

	/**
	 * javax.swing.JButton
	 */
	/**
	 * cancel
	 */
	/**
	 * This is a JButton variable cancel.
	 */
	private javax.swing.JButton cancel;

	/**
	 * javax.swing.JPanel
	 */
	/**
	 * buttonsText
	 */
	/**
	 * This is a JPanel variable, holds textboxes.
	 */

	private javax.swing.JPanel buttonsText;

	/**
	 * javax.swing.JPanel
	 */
	/**
	 * units
	 */
	/**
	 * This is a JPanel variable holds units radio buttons.
	 */

	private javax.swing.JPanel units;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * inches
	 */
	/**
	 * This is a JRadioButton variable inches.
	 */

	private javax.swing.JRadioButton inches;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * cm
	 */
	/**
	 * This is a JRadioButton variable cm.
	 */

	private javax.swing.JRadioButton cm;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * pixels
	 */
	/**
	 * This is a JRadioButton variable pixels.
	 */

	private javax.swing.JRadioButton pixels;

	/**
	 * javax.swing.JPanel
	 */
	/**
	 * widthHeight
	 */
	/**
	 * This is a JPanel variable holds the textboxes for width and height.
	 */

	private javax.swing.JPanel widthHeight;

	/**
	 * javax.swing.JTextField
	 */
	/**
	 * widthText
	 */
	/**
	 * This is a JTextField variable width.
	 */

	private javax.swing.JTextField widthText;

	/**
	 * javax.swing.JTextField
	 */
	/**
	 * heightText
	 */
	/**
	 * This is a JTextField variable height.
	 */

	private javax.swing.JTextField heightText;

	/**
	 * javax.swing.JLabel
	 */
	/**
	 * Height
	 */
	/**
	 * This is a JLabel variable to signify height textbox name.
	 */

	private javax.swing.JLabel Height;

	/**
	 * javax.swing.JLabel
	 */
	/**
	 * Width
	 */
	/**
	 * This is a JLabel variable to signify width textbox name.
	 */

	private javax.swing.JLabel Width;

	/**
	 * javax.swing.JLabel
	 */
	/**
	 * color
	 */
	/**
	 * This is a JLabel variable.
	 */

	private javax.swing.JPanel color;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * colorScale
	 */
	/**
	 * This is a JRadioButton variable.
	 */

	private javax.swing.JRadioButton colorScale;

	/**
	 * javax.swing.JRadioButton
	 */
	/**
	 * blackwhite
	 */
	/**
	 * This is a JRadioButton variable.
	 */

	private javax.swing.JRadioButton blackwhite;

	// End of variables declaration
}
/** simple subclass representing a choice OK or CANCEL.*/

class choice2 {
	/**
	 * YES = 0.
	 */
	public static int OK = 1;

	/**
	 * CANCEL = 1.
	 */
	public static int CANCEL = 0;
}

