package jabberpoint;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/** <p>A tekst item.</p>
 * <p>A jabberpoint.TextItem has drawingfunctionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem
{
	private final String text;
	
	private static final String EMPTY_TEXT = "No Text Given";

// a textitem of level level, with the text string
	public TextItem(int level, Style style, String text)
	{
		super(level, style);
		this.text = text;
	}

// an empty textitem
	public TextItem() {
		this(0, Style.getStyle(0), EMPTY_TEXT);
	}

// give the text
	public String getText() {
		return text == null ? "" : text;
	}

// geef de AttributedString voor het item
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, getText().length());
		return attrStr;
	}

// give the bounding box of the item
@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
	{
		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		int xsize = 0, ysize = (int) (myStyle.leading * scale);

		for (TextLayout layout : layouts)
		{
			Rectangle2D bounds = layout.getBounds();
			xsize = (int) Math.max(xsize, bounds.getWidth());
			ysize += (int) (bounds.getHeight() + layout.getLeading() + layout.getDescent());
		}

		return new Rectangle((int)(myStyle.indent * scale), 0, xsize, ysize);
	}

// draw the item
	@Override
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
	{
		if (getText().isEmpty()) return;

		List<TextLayout> layouts = getLayouts(g, myStyle, scale);

		Point pen = new Point(x + (int)(myStyle.indent * scale), y + (int)(myStyle.leading * scale));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myStyle.color);

		for (TextLayout layout : layouts)
		{
			pen.y += (int) layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += (int) layout.getDescent();
		}
	}

	private List<TextLayout> getLayouts(Graphics g, Style s, float scale)
	{
		List<TextLayout> layouts = new ArrayList<>();

		AttributedString attrStr = getAttributedString(s, scale);
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext frc = g2d.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);

		// Assume Slide.WIDTH is defined; adjust wrappingWidth as needed.
		float wrappingWidth = (Slide.WIDTH - s.indent) * scale;

		while (measurer.getPosition() < getText().length())
		{
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}

		return layouts;
	}

	public String toString() {
		return "jabberpoint.TextItem[" + getLevel()+","+getText()+"]";
	}
}
