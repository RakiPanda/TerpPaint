/*
 * emboss.java
 *
 * Created on April 1, 2002, 12:08 AM
 */

/**
 *
 * @author  Terp Paint
 * @version 2.0
 */
// ming 4.26

// ming 4.26 end
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.LinkedList;

/**
 * A emboss object is a dialogue box that allows the user to emboss the entire
 * image. The user can then edit the options in the dialog box, and apply the
 * changes to the image. Dialog box is terminated by clicking on the 'OK' or
 * 'Cancel' buttons. OK will instantiated the changes, Cancel will do nothing to
 * the original image. The user can assign the direction of light source as 8
 * possible directions, 0, 45, 90, 135, 180, 225, 270, 315, for the emboss
 * effect. There are no OS/Hardware dependencies and no variances. There is no
 * need for any security constraints and no references to external
 * specifications.
 */
public class emboss extends javax.swing.JDialog {

	/**
	 * Constructor initializes the emboss dialog box and displays it and waits
	 * for user input. There are no OS/Hardware dependencies and no variances.
	 * There is no need for any security constraints and no references to
	 * external specifications.
	 * 
	 * @param parent
	 *            JFrame
	 * @param modal
	 *            boolean which must be true
	 */
	public emboss(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor. There are no OS/Hardware dependencies and
	 * no variances. There is no need for any security constraints and no
	 * references to external specifications.
	 */
	public void initComponents() {
		//  ok_action = false;
		outterButtons = new javax.swing.ButtonGroup();
		degrees = new javax.swing.ButtonGroup();
		ok_cancel = new javax.swing.JPanel();
		ok = new javax.swing.JButton();
		cancel = new javax.swing.JButton();
		choices = new javax.swing.JPanel();
		north = new javax.swing.JRadioButton();
		north_west = new javax.swing.JRadioButton();
		west = new javax.swing.JRadioButton();
		south_west = new javax.swing.JRadioButton();
		south = new javax.swing.JRadioButton();
		south_east = new javax.swing.JRadioButton();
		east = new javax.swing.JRadioButton();
		north_east = new javax.swing.JRadioButton();

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		ok_cancel.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints1;

		ok.setText("OK");
		ok.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okActionPerformed(evt);
			}
		});

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		ok_cancel.add(ok, gridBagConstraints1);

		cancel.setText("CANCEL");
		cancel.setText("キャンセル");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		ok_cancel.add(cancel, gridBagConstraints1);

		getContentPane().add(ok_cancel, java.awt.BorderLayout.EAST);

//		choices.setBorder(new javax.swing.border.TitledBorder("LIGHT"));
		choices.setBorder(new javax.swing.border.TitledBorder("引き伸ばす方向"));
		choices.setPreferredSize(new java.awt.Dimension(150, 240)); // 日本語化に伴ってサイズを調整
		choices.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints2;

		north.setSelected(true);
//		north.setText("North");
		north.setText("北");
		outterButtons.add(north);
		/*
		 * north.addActionListener(new java.awt.event.ActionListener() { public
		 * void actionPerformed(java.awt.event.ActionEvent evt) {
		 * northlActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(north, gridBagConstraints2);

//		north_west.setText("Northwest");
		north_west.setText("北西");
		outterButtons.add(north_west);
		/*
		 * north_west.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * north_westActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(north_west, gridBagConstraints2);

//		west.setText("West");
		west.setText("西");
		outterButtons.add(west);
		/*
		 * west.addActionListener(new java.awt.event.ActionListener() { public
		 * void actionPerformed(java.awt.event.ActionEvent evt) {
		 * westActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 2;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(west, gridBagConstraints2);

//		south_west.setText("Southwest");
		south_west.setText("南西");
		outterButtons.add(south_west);
		/*
		 * south_west.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * south_westActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 3;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(south_west, gridBagConstraints2);

//		south.setText("South");
		south.setText("南");
		outterButtons.add(south);
		/*
		 * south.addActionListener(new java.awt.event.ActionListener() { public
		 * void actionPerformed(java.awt.event.ActionEvent evt) {
		 * southActionPerformed(evt); } });
		 */

		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 4;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(south, gridBagConstraints2);

//		south_east.setText("Southeast");
		south_east.setText("南東");
		outterButtons.add(south_east);
		/*
		 * south_east.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * south_eastActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 5;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(south_east, gridBagConstraints2);

//		east.setText("East");
		east.setText("東");
		outterButtons.add(east);
		/*
		 * east.addActionListener(new java.awt.event.ActionListener() { public
		 * void actionPerformed(java.awt.event.ActionEvent evt) {
		 * eastActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 6;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(east, gridBagConstraints2);

//		north_east.setText("Northeast");
		north_east.setText("北東");
		outterButtons.add(north_east);
		/*
		 * north_east.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * north_eastActionPerformed(evt); } });
		 */
		gridBagConstraints2 = new java.awt.GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 7;
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
		choices.add(north_east, gridBagConstraints2);

		getContentPane().add(choices, java.awt.BorderLayout.CENTER);

		pack();
	}

	/**
	 * This method is done by mouse event to emboss the image. There are no
	 * OS/Hardware dependencies and no variances. There is no need for any
	 * security constraints and no references to external specifications.
	 * 
	 * @param evt
	 *            is performed by mouse action event.
	 */
	public void okActionPerformed(java.awt.event.ActionEvent evt) {
		// Add your handling code here:
		// ming 4.24
		int cur_layer = ((TerpPaint) this.getParent()).center.currentLayer;
		LinkedList cur_action_list = (LinkedList) (((TerpPaint) this
				.getParent()).action_list.get(cur_layer));
		LinkedList cur_redo_list = (LinkedList) (((TerpPaint) this.getParent()).redo_list
				.get(cur_layer));

		int temp1 = cur_redo_list.size();
		for (int i = 0; i < temp1; i++)
			cur_redo_list.removeLast();

		if (((TerpPaint) this.getParent()).toolSelectall.getSelected()
				|| ((TerpPaint) this.getParent()).toolSelect.getSelected()
				|| ((TerpPaint) this.getParent()).toolMagicSelect.getSelected()) {
			cur_action_list.add("Region emboss");
			BufferedImage selected;
			if (((TerpPaint) this.getParent()).currentTool == ((TerpPaint) this
					.getParent()).toolSelect) {
				selected = ((TerpPaint) this.getParent()).toolSelect
						.getCopyImage(((TerpPaint) this.getParent()).center);
			} else if (((TerpPaint) this.getParent()).currentTool == ((TerpPaint) this
					.getParent()).toolMagicSelect) {
				selected = ((TerpPaint) this.getParent()).toolMagicSelect
						.getCopyImage(((TerpPaint) this.getParent()).center);
			} else {
				selected = ((TerpPaint) this.getParent()).toolSelectall
						.getCopyImage(((TerpPaint) this.getParent()).center);
			}

			Kernel kernel = new Kernel(3, 3, new float[] { 0, 0, 0, 0, 0, 0, 0,
					0, 0 });

			if (north.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, -2, 0, 0, 1, 0, 0,
						2, 0 });
			} else if (north_west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { -2, 0, 0, 0, 1, 0, 0,
						0, 2 });
			} else if (west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 0, -2, 1, 2, 0,
						0, 0 });
			} else if (south_west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 2, 0, 1, 0, -2,
						0, 0 });
			} else if (south.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 2, 0, 0, 1, 0, 0,
						-2, 0 });
			} else if (south_east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 2, 0, 0, 0, 1, 0, 0, 0,
						-2 });
			} else if (east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 0, 2, 1, -2, 0,
						0, 0 });
			} else if (north_east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, -2, 0, 1, 0, 2,
						0, 0 });
			}
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					hints);

			selected = op.filter(selected, null);
			if (((TerpPaint) this.getParent()).currentTool == ((TerpPaint) this
					.getParent()).toolSelect) {
				((TerpPaint) this.getParent()).toolSelect.setPastedImage(
						selected, ((TerpPaint) this.getParent()).center,
						((TerpPaint) this.getParent()).toolSelect.getStartX(),
						((TerpPaint) this.getParent()).toolSelect.getStartY());
				//	  ((TerpPaint)this.getParent()).toolSelect.backupImage =
				// ((TerpPaint)this.getParent()).center.getBufferedImage();
			} else if (((TerpPaint) this.getParent()).currentTool == ((TerpPaint) this
					.getParent()).toolSelectall) {
				((TerpPaint) this.getParent()).toolSelectall.setPastedImage(
						selected, ((TerpPaint) this.getParent()).center,
						((TerpPaint) this.getParent()).toolSelectall
								.getStartX(),
						((TerpPaint) this.getParent()).toolSelectall
								.getStartY());
				//	 ((TerpPaint)this.getParent()).toolSelectall.backupImage =
				// ((TerpPaint)this.getParent()).center.getBufferedImage();
			}
			//	   else if (((TerpPaint)this.getParent()).currentTool ==
			// ((TerpPaint)this.getParent()).magicSelectTool)
			//	  ((TerpPaint)this.getParent()).magicSelectTool.setPastedImage(selected,
			// ((TerpPaint)this.getParent()).center,
			// ((TerpPaint)this.getParent()).magicSelectTool.getStartX(),
			// ((TerpPaint)this.getParent()).magicSelectTool.getStartY());
			//BufferedImage curImage =
			// ((TerpPaint)this.getParent()).center.getBufferedImage();
			//((TerpPaint)this.getParent()).center.setBufferedImage(selected);//curImage);
			//((TerpPaint)this.getParent()).center.repaint();

		} else {
			cur_action_list.add("Image emboss");
			// ming 4.24 end
			BufferedImage im = ((TerpPaint) this.getParent()).center
					.getBufferedImage();

			//	  int x = Integer.parseInt(sharpen_value.getText());

			Kernel kernel = new Kernel(3, 3, new float[] { 0, 0, 0, 0, 0, 0, 0,
					0, 0 });

			if (north.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, -2, 0, 0, 1, 0, 0,
						2, 0 });
			} else if (north_west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { -2, 0, 0, 0, 1, 0, 0,
						0, 2 });
			} else if (west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 0, -2, 1, 2, 0,
						0, 0 });
			} else if (south_west.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 2, 0, 1, 0, -2,
						0, 0 });
			} else if (south.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 2, 0, 0, 1, 0, 0,
						-2, 0 });
			} else if (south_east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 2, 0, 0, 0, 1, 0, 0, 0,
						-2 });
			} else if (east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, 0, 2, 1, -2, 0,
						0, 0 });
			} else if (north_east.isSelected()) {
				kernel = new Kernel(3, 3, new float[] { 0, 0, -2, 0, 1, 0, 2,
						0, 0 });
			}
			// ming 4.24
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					hints);
			// ming 4.24 end

			im = op.filter(im, null);

			Graphics2D gd = im.createGraphics();

			gd.drawImage(im, 0, 0, null);
			((TerpPaint) this.getParent()).center.setBufferedImage(im);
			im.flush();
			// ming 4.24
		}
		// ming 4.24 end
		// ming 4.26

		// ming 4.26 end
		((TerpPaint) this.getParent()).updateUndoList();
		this.closeDialog(new java.awt.event.WindowEvent(this, 0));
	}

	/**
	 * Closes the dialog. There are no OS/Hardware dependencies and no
	 * variances. There is no need for any security constraints and no
	 * references to external specifications.
	 * 
	 * @param evt
	 *            performed by mouse event
	 */
	public void closeDialog(java.awt.event.WindowEvent evt) {
		setVisible(false);
		dispose();
	}

	/**
	 * This method cancels rotating the image. There are no OS/Hardware
	 * dependencies and no variances. There is no need for any security
	 * constraints and no references to external specifications.
	 * 
	 * @param evt
	 *            performed by mouse event
	 */
	public void cancelActionPerformed(java.awt.event.ActionEvent evt) {
		setVisible(false);
		dispose();
	}

	/**
	 * Creates a new emboss object. There are no OS/Hardware dependencies and no
	 * variances. There is no need for any security constraints and no
	 * references to external specifications.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new emboss(new javax.swing.JFrame(), true).show();
	}

	// Variables declaration - do not modify
	/** java swing GUI depicting the button group called outterButtons. */
	private javax.swing.ButtonGroup outterButtons;

	/** java swing GUI depicting the button group called degrees. */
	private javax.swing.ButtonGroup degrees;

	/** java swing GUI depicting the JPanel called ok_cancel. */
	private javax.swing.JPanel ok_cancel;

	/** java swing GUI depicting the Jbutton called ok. */
	private javax.swing.JButton ok;

	/** java swing GUI depicting the Jbutton called cancel. */
	private javax.swing.JButton cancel;

	/** java swing GUI depicting the JPanel called choices. */
	private javax.swing.JPanel choices;

	/** java swing GUI depicting the JRadioButton called north. */
	private javax.swing.JRadioButton north;

	/** java swing GUI depicting the JRadioButton called north_west. */
	private javax.swing.JRadioButton north_west;

	/** java swing GUI depicting the JRadioButton called west. */
	private javax.swing.JRadioButton west;

	/** java swing GUI depicting the JRadioButton called south_west. */
	private javax.swing.JRadioButton south_west;

	/** java swing GUI depicting the JRadioButton called south. */
	private javax.swing.JRadioButton south;

	/** java swing GUI depicting the JRadioButton called south_east. */
	private javax.swing.JRadioButton south_east;

	/** java swing GUI depicting the JRadioButton called east. */
	private javax.swing.JRadioButton east;

	/** java swing GUI depicting the JRadioButton called north_east. */
	private javax.swing.JRadioButton north_east;

	/**
	 * The dialog is closed by OK
	 */
	//  public boolean ok_action;
	// End of variables declaration
}