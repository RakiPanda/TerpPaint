import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

/** Layer is used to create a new main canvas to draw upon
 * The OS is windows.
 * There is no implementation variances.
 * There is no security constaints.
 * There is no external secification.
 */
public class Layer extends JFrame{

    /**The Layer constructor is called by the AllLayer class when pressing the add button
     *It should work with all operating systems and hardware.
     *There are no variances and no security constraints.
     *@param theCanvas main_canvas
     */
    public Layer(main_canvas theCanvas) {
	all = (AllLayers)this.getParent();
//	Name = "Layer " + String.valueOf(number);
	Name = "レイヤー " + String.valueOf(number);
	number++;
	/*FAULT::Name = "Layer " + String.valueOf(number);*/
	layerCanvas = theCanvas;
	initComponents();

    }

    /**This function initializes the GUI components of the Layer class and is
     *only called by the Layer constructor
     *It should work with all operating systems and hardware.
     *There are no variances and no security constraints.
     */
    public void initComponents(){
	current = new JRadioButtonMenuItem();
current.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/junk.gif")));
	 //visible = new JCheckBox();
	//visible.setSelected(true);
popUp = new JFrame();
popUp.setVisible(false);
	nameLab = new JLabel(Name);
	change = new JButton("Change Name");
	/*FAULT::change.setText("change poop");*/

	//layering = new main_canvas();

	//getContentPane().add(layering, java.awt.BorderLayout.CENTER);
	//layering.setBackground(Color.black);
	//layering.requestFocus();
	current.addActionListener (new ActionListener (){
	    public void actionPerformed(ActionEvent evt) {
		currentActionPerformed(evt);
	    }
	});

	/*visible.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent evt) {
		visibleActionPerformed(evt);
	    }
	});*/

	change.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent evt) {
		changeActionPerformed(evt);
	    }
	});

    }

    /**This function sets popUp frame to be not visible.
     */
    public void removeLayer() {
	popUp.setVisible(false);
	/*FAULT::popUp.setVisible(true);*/
    }


   /**This function sets the current layer to display
    *It should work with all operating systems and hardware.
     *There are no variances and no security constraints.
     *@param evt ActionEvent
     */
   public void currentActionPerformed (ActionEvent evt) {
       if (current.isSelected())
       {
	    layerCanvas.changeLayer(Name);
	    /*FAULT::layerCanvas.layerList.add(this);*/
	    // ming 4.26
	    //paint.updateUndoList();
	//    ((TerpPaint)this.getParent()).updateUndoList();

	     // ming 4.26 end
       }
   }
    /*
   public void visibleActionPerformed(ActionEvent evt) {
	if (visible.isSelected() == true)
	{
	    popUp.setVisible(true);
	}
	else
	{
	    popUp.setVisible(false);
	}
    }
    */
   /**This function is called by change button on GUI
    *It changes the name of the single layer
    *It should work with all operating systems and hardware.
     *There are no variances and no security constraints.
     *@param evt ActionEvent
     */
    public void changeActionPerformed(ActionEvent evt) {
	temp = new JFrame();
	temp.setLocation( (int) temp.getLocation().getX()+300, (int) temp.getLocation().getY()+300);
//	temp.setTitle("New Layer Name");
	temp.setTitle("新規レイヤーの名前");
	temp.setVisible(true);
	JButton ok = new JButton("OK");
//	JButton cancel = new JButton("Cancel");
	JButton cancel = new JButton("キャンセル");
	JPanel ok_cancel = new JPanel();
	ok_cancel.setLayout(new GridBagLayout());
	GridBagConstraints constraints;
	constraints = new GridBagConstraints();
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.anchor = java.awt.GridBagConstraints.WEST;
	ok_cancel.add(ok, constraints);
	constraints.gridx = 0;
	constraints.gridy = 1;
	ok_cancel.add(cancel, constraints);

	ok.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		okActionPerformed(evt);
	    }
	});

	cancel.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		cancelActionPerformed(evt);
	    }
	});

//	JLabel message = new JLabel("Enter new Layer Name");
	JLabel message = new JLabel("新規レイヤーの名前を入力してください");
	area = new JTextField("",20);
	JPanel info = new JPanel();
	info.setLayout(new GridBagLayout());
	constraints.gridx = 0;
	constraints.gridy = 0;
	constraints.anchor = java.awt.GridBagConstraints.WEST;
	info.add(message, constraints);
	constraints.gridx = 0;
	constraints.gridy = 1;
	constraints.anchor = java.awt.GridBagConstraints.WEST;
	info.add(area, constraints);


	temp.getContentPane().add(info, BorderLayout.WEST);
	temp.getContentPane().add(ok_cancel, BorderLayout.EAST);
	temp.pack();
    }

    /**This function operates when GUI user clicks on ok
     *The function stores new name of the layer and changes the label to correct
     *name
     *It should work with all operating systems and hardware.
     *There are no variances and no security constraints.
     *@param evt ActionEvent
     */
    public void okActionPerformed(ActionEvent evt) {
	String newName = new String();
	String oldName;
	newName = area.getText();
	boolean nameexist = false;
	boolean space = false;
	System.out.println(Name+newName);
	for (int i=0; i <layerCanvas.nameList.size()&&!nameexist; i++){
	    if( ((String)(layerCanvas.nameList.get(i))).equals(newName) && !Name.equals(newName)){
	      layerCanvas.nameList.set(i, newName);
	      nameexist =true;
	    }
	}
	if(newName.equals("")){
		space = true;
	}
	if(nameexist == false && !space){
	oldName = Name;
	Name = newName;
	nameLab.setText(Name);
	System.out.println(Name);
	boolean found  = false;
	for (int i=0; i <layerCanvas.nameList.size()&&!found; i++){
	    if( ((String)(layerCanvas.nameList.get(i))).equals(oldName) ){
	      layerCanvas.nameList.set(i, newName);
	      found =true;
	    }
	}
	
	temp.setVisible(false);
	temp.dispose();
	}
	else if(space){
		space = false;
		JOptionPane.showMessageDialog(this,
			    "Empty Name",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);

	}
	else{
		nameexist = false;
		JOptionPane.showMessageDialog(this,
			    "Name already exist",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}
    }

    /**This function operates when GUI user clicks on cancel
     *Nothing is saved and the temporary frame for remove is disappeared.
     *@param evt ActionEvent
     */
    public void cancelActionPerformed(java.awt.event.ActionEvent evt) {
	temp.setVisible(false);
	temp.dispose();
	/*FAULT::temp.setVisible(true);*/
    }

    /**This is the main and does initializing.
     *@param args String[]
     */
    public static void main(String args[]) {
	//new Layer();
	//number++;
    }

    /** paint is a TerpPaint value that holds the drawing board.
     */
    // ming 4.26
    public AllLayers all;//Ronald phase 5
    // ming 4.26 end

    /**GUI JRadioButtonMenuItem for current layer.
     */
    public JRadioButtonMenuItem current;
    /**layerCanvas is the main_canvas object for which each layer can draw.
     */
    public main_canvas layerCanvas;
    /**popUp is frame so for the remove function so that the name can be typed.
     */
    public JFrame popUp;

    /**JCheckBox that determines visibility.
     */
    public JCheckBox visible;

    /**JButton that determines the changing of the name of layer.
     */
    public JButton change;

    /**JTextField that determines the name of the layer that user types.
     */
    public JTextField area;

    /**JFrame that opens the temp for remove function.
     */
    public JFrame temp;

    /**nameLab is JLabel that labels the name of the layer.
     */
    public JLabel nameLab;

    /**number to store the position within the gridbagconstraints.
     */
    public static int number = 1;

    /**String to store the name of the layer.
     */
    public String Name = new String();
}
