package jabberpoint;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * The abstract base class for an item on a slide.
 * All SlideItems have drawing functionality.
 */
public abstract class SlideItem implements SlideComponent
{
    private int level = 0; // level of the slide item
    private Style style;

    public SlideItem(int level, Style style)
    {
        this.level = level;
        this.style = style;
    }

    /**
     * Default constructor creates a SlideItem at level 0 with a default style.
     */
    public SlideItem()
    {
        this(0, Style.getStyle(0));
    }

    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public Style getStyle()
    {
        return this.style;
    }

    public void setStyle(Style style)
    {
        this.style = style;
    }

    public abstract Rectangle getBoundingBox(Graphics g,
                                             ImageObserver observer,
                                             float scale,
                                             Style style);

    public abstract void draw(int x, int y, float scale,
                              Graphics g,
                              Style style,
                              ImageObserver observer);

    @Override
    public int draw(Graphics g, int x, int y)
    {
        this.draw(x, y, 1.0f, g, this.style, null);
        return y + this.style.leading + g.getFontMetrics(this.style.getFont(1.0f)).getHeight();
    }
}
