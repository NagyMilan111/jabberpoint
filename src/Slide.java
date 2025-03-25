import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.Vector;

/**
 * <p>A slide. This class has a drawing functionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide implements SlideComponent
{
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	protected String title; // title is saved separately
	protected Vector<SlideComponent> components; // slide items are saved in a Vector

	public Slide()
	{
		this.components = new Vector<SlideComponent>();
	}

	// Add a slide item
	public void append(SlideComponent comp)
	{
		components.add(comp);
	}

	public void append(int level, String message)
	{
		append(SlideItemFactory.createSlideItem("text", level, Style.getStyle(level), message));
	}

	// give the title of the slide
	public String getTitle()
	{
		return title;
	}

	// change the title of the slide
	public void setTitle(String newTitle)
	{
		title = newTitle;
	}

	// give the  SlideItem
	public SlideComponent getSlideItem(int number)
	{
		return components.get(number);
	}

	// give all SlideItems in a Vector
	public Vector<SlideComponent> getSlideItems()
	{
		return components;
	}

	// give the size of the Slide
	public int getSize()
	{
		return components.size();
	}

	// draw the slide
	public void draw(Graphics g, Rectangle area, ImageObserver view)
	{
		float scale = getScale(area);
		int y = area.y;


		// Title is handled separately
		SlideItem slideItem = new TextItem(0, getTitle());
		Style style = Style.getStyle(slideItem.getLevel());
		slideItem.draw(area.x, y, scale, g, style, view);
		y += slideItem.getBoundingBox(g, view, scale, style).height;


		for (int number = 0; number < getSize(); number++)
		{
			slideItem = (SlideItem) getSlideItems().elementAt(number);
			style = Style.getStyle(slideItem.getLevel());
			slideItem.draw(area.x, y, scale, g, style, view);
			y += slideItem.getBoundingBox(g, view, scale, style).height;
		}
	}

	// Give the scale for drawing
	private float getScale(Rectangle area)
	{
		return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
	}

	@Override
	public int draw(Graphics graphics, int x, int y)
	{
		return 0;
	}
}
