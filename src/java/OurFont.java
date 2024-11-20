/*
 * OurFont.java
 *
 * Created on April 7, 2002, 11:24 AM
 */

/** initializes the font and holds 6 public variables. The boolean fields "bold",
 * "underline", and "italics" are all set to false as default. The int size is
 * set to 8 as default.
 * @author Administrator
 * @version 1.1
 */
public class OurFont {
    /** initializes the public boolean field "bold" to false.
     */
    public boolean bold = false;
    /** initializes the public boolean "underline" to false.
     */
    public boolean underline = false;
    /** initializes the public boolean "italics" to false.
     */
    public boolean italics = false;
    /** initializes the int "size" to be 8 for the font.
     */
    public int size = 8;
    /** initializes the String "font" to be empty. It is probably reset when the user
     * chooses a font of their own.
     */
    public String font = "";
    /** initializes the String text. It is probably set when the user types in text
     * using the font.
     */
    public String text;
}
