package jabberpoint;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/** <p>The abstract class for an item on a slide<p>
 * <p>All SlideItems have drawingfunctionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public abstract class SlideItem implements SlideComponent{
	private int level = 0; // level of the slideitem
	private Style style;

	public SlideItem(int level, Style style)
	{
		this.level = level;
		this.style = style;
	}

	/**
	 * Default constructor creates a SlideItem at level 0 with a default style.
	 */
	public SlideItem() {
		this(0, Style.getStyle(0));
	}

// Give the level
	public int getLevel() {
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public Style getStyle()
	{
		return style;
	}

	public void setStyle(Style style)
	{
		this.style = style;
	}

	// Give the bounding box
	public abstract Rectangle getBoundingBox(Graphics g,
			ImageObserver observer, float scale, Style style);

// Draw the item
	public abstract void draw(int x, int y, float scale,
			Graphics g, Style style, ImageObserver observer);

	@Override
	public int draw(Graphics g, int x, int y) {
		// You can adjust this default behavior as needed.
		draw(x, y, 1.0f, g, style, null);
		// Advance y by a default spacing (e.g., style leading plus font height)
		return y + style.leading + g.getFontMetrics(style.getFont(1.0f)).getHeight();
	}
}
