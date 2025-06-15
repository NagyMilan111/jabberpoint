package jabberpoint;

import java.awt.*;

import static jabberpoint.Constants.FONT_NAME;

/**
 * Style represents visual layout properties like indent, color, font size, and line spacing (leading).
 * Each level of slide item (0–4) has a corresponding style.
 */
public class Style
{

    private static Style[] styles;
    protected int indent;
    protected Color color;
    protected Font font;
    protected int fontSize;
    protected int leading;

    public Style(int indent, Color color, int points, int leading)
    {
        this.indent = indent;
        this.color = color;
        this.fontSize = points;
        this.leading = leading;
        this.font = new Font(FONT_NAME, Font.BOLD, points);
    }

    public static void createStyles()
    {
        styles = new Style[5];
        styles[0] = new Style(0, Color.red, 48, 20);     // Level 0
        styles[1] = new Style(20, Color.blue, 40, 10);   // Level 1
        styles[2] = new Style(50, Color.black, 36, 10);  // Level 2
        styles[3] = new Style(70, Color.black, 30, 10);  // Level 3
        styles[4] = new Style(90, Color.black, 24, 10);  // Level 4
    }

    public static Style getStyle(int level)
    {
        if (styles == null)
        {
            createStyles();
        }
        if (level >= styles.length)
        {
            level = styles.length - 1;
        }
        return styles[level];
    }

    @Override
    public String toString()
    {
        return "[" + this.indent + "," + this.color + "; " + this.fontSize + " on " + this.leading + "]";
    }

    public Font getFont(float scale)
    {
        return this.font.deriveFont(this.fontSize * scale);
    }

    public Font getFont()
    {
        return this.font;
    }
}
