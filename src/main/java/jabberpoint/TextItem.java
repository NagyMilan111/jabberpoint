package jabberpoint;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

import static jabberpoint.Constants.EMPTY_TEXT;
import static jabberpoint.Constants.SLIDEWIDTH;

/**
 * A TextItem represents a block of text in a slide.
 * It has drawing functionality and formatting styles.
 */
public class TextItem extends SlideItem
{

    private final String text;

    // A text item with a given level, style, and content
    public TextItem(int level, Style style, String text)
    {
        super(level, style);
        this.text = text;
    }

    // An empty text item
    public TextItem()
    {
        this(0, Style.getStyle(0), EMPTY_TEXT);
    }

    public String getText()
    {
        return this.text == null ? "" : this.text;
    }

    public AttributedString getAttributedString(Style style, float scale)
    {
        AttributedString attrStr = new AttributedString(this.getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.getText().length());
        return attrStr;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
    {
        List<TextLayout> layouts = this.getLayouts(g, myStyle, scale);
        int xsize = 0;
        int ysize = (int) (myStyle.leading * scale);

        for (TextLayout layout : layouts)
        {
            Rectangle2D bounds = layout.getBounds();
            xsize = (int) Math.max(xsize, bounds.getWidth());
            ysize += (int) (bounds.getHeight() + layout.getLeading() + layout.getDescent());
        }

        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
    {
        if (this.getText().isEmpty()) return;

        List<TextLayout> layouts = this.getLayouts(g, myStyle, scale);

        Point pen = new Point(x + (int) (myStyle.indent * scale), y + (int) (myStyle.leading * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.color);

        for (TextLayout layout : layouts)
        {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style style, float scale)
    {
        List<TextLayout> layouts = new ArrayList<>();

        AttributedString attrStr = this.getAttributedString(style, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);

        float wrappingWidth = (SLIDEWIDTH - style.indent) * scale;

        while (measurer.getPosition() < this.getText().length())
        {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    @Override
    public String toString()
    {
        return "TextItem[" + this.getLevel() + "," + this.getText() + "]";
    }
}
