package jabberpoint;

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
		this.components = new Vector<>();
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
		SlideComponent slideComponent = SlideItemFactory.createSlideItem("text",0, Style.getStyle(0), getTitle());
		slideComponent.draw(g, area.x, y);
		y += ((TextItem) slideComponent).getBoundingBox(g, view, scale, Style.getStyle(0)).height;

		for (int number = 0; number < getSize(); number++)
		{
			SlideComponent component = getSlideItems().elementAt(number);
			Style style = Style.getStyle(getComponentLevel(component));
			component.draw(g, area.x, y);
			y += ((SlideItem) component).getBoundingBox(g, view, scale, style).height;
		}
	}

	@Override
	public int draw(Graphics g, int x, int y)
	{
		// Create a default area using the starting x and y, or use the JFrame's dimensions.
		Rectangle area = new Rectangle(x, y, Slide.WIDTH, Slide.HEIGHT);
		// Call your custom drawing method
		draw(g, area, null);
		// Return an updated y if needed
		return y;
	}

	// Give the scale for drawing
	private float getScale(Rectangle area) {
		return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
	}

	private int getComponentLevel(SlideComponent comp) {
		if (comp instanceof SlideItem)
			return ((SlideItem) comp).getLevel();
		return 0;
	}
}
