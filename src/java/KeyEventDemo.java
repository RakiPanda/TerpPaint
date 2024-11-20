/*
 * Swing version
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

/** 
 * Class KeyEventDemo is a debugging class used to show Key Events 
 *
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 *
 * @author Terp Paint
 * @version 4.0
 */
public class KeyEventDemo extends JApplet
			  implements KeyListener,
				     ActionListener {
    
    /** JTextArea object used for debugging.*/
    JTextArea displayArea;
    /** JTextField object used for debugging.*/
    JTextField typingArea;
    
    /** String object holding the newline char. */
    static final String newline = "\n";

    public void init() {
	JButton button = new JButton("Clear");
	button.addActionListener(this);

	typingArea = new JTextField(20);
	typingArea.addKeyListener(this);

	displayArea = new JTextArea();
	displayArea.setEditable(false);
	JScrollPane scrollPane = new JScrollPane(displayArea);
	scrollPane.setPreferredSize(new Dimension(375, 125));

	JPanel contentPane = new JPanel();
	contentPane.setLayout(new BorderLayout());
	contentPane.add(typingArea, BorderLayout.NORTH);
	contentPane.add(scrollPane, BorderLayout.CENTER);
	contentPane.add(button, BorderLayout.SOUTH);
	setContentPane(contentPane);
    }

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
	displayInfo(e, "KEY TYPED: ");
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
	displayInfo(e, "KEY PRESSED: ");
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
	displayInfo(e, "KEY RELEASED: ");
    }

    /** Handle the button click. */
    public void actionPerformed(ActionEvent e) {
	//Clear the text components.
	displayArea.setText("");
	typingArea.setText("");

	//Return the focus to the typing area.
	typingArea.requestFocus();
    }

    /**
     * We have to jump through some hoops to avoid
     * trying to print non-printing characters
     * such as Shift.  (Not only do they not print,
     * but if you put them in a String, the characters
     * afterward won't show up in the text area.).
     *
     * @param e  a KeyEvent object
     * @param s  a String of what is produced by the function
     */
    protected void displayInfo(KeyEvent e, String s){
	String charString, keyCodeString, modString, tmpString;

	char c = e.getKeyChar();
	int keyCode = e.getKeyCode();
	int modifiers = e.getModifiers();

	if (Character.isISOControl(c)) {
	    charString = "key character = "
		       + "(an unprintable control character)";
	} else {
	    charString = "key character = '"
		       + c + "'";
	}

	keyCodeString = "key code = " + keyCode
			+ " ("
			+ KeyEvent.getKeyText(keyCode)
			+ ")";

	modString = "modifiers = " + modifiers;
	tmpString = KeyEvent.getKeyModifiersText(modifiers);
	if (tmpString.length() > 0) {
	    modString += " (" + tmpString + ")";
	} else {
	    modString += " (no modifiers)";
	}

	displayArea.append(s + newline
			   + "	  " + charString + newline
			   + "	  " + keyCodeString + newline
			   + "	  " + modString + newline);
    }
}
