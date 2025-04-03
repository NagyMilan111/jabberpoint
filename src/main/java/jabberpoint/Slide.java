package jabberpoint;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * A Slide. This class has drawing functionality.
 */
public class Slide implements SlideComponent
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    protected String title;
    protected Vector<SlideComponent> components;

    public Slide()
    {
        this.components = new Vector<>();
    }

    // Add a slide item
    public void append(SlideComponent comp)
    {
        this.components.add(comp);
    }

    public void append(int level, String message)
    {
        this.append(SlideItemFactory.createSlideItem("text", level, Style.getStyle(level), message));
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    public SlideComponent getSlideItem(int number)
    {
        return this.components.get(number);
    }

    public Vector<SlideComponent> getSlideItems()
    {
        return this.components;
    }

    public int getSize()
    {
        return this.components.size();
    }

    // Draw the slide
    public void draw(Graphics g, Rectangle area, ImageObserver view)
    {
        float scale = this.getScale(area);
        int y = area.y;

        // Draw title separately
        SlideComponent slideComponent = SlideItemFactory.createSlideItem("text", 0, Style.getStyle(0), this.getTitle());
        slideComponent.draw(g, area.x, y);
        y += ((TextItem) slideComponent).getBoundingBox(g, view, scale, Style.getStyle(0)).height;

        for (int number = 0; number < this.getSize(); number++)
        {
            SlideComponent component = this.components.elementAt(number);
            Style style = Style.getStyle(this.getComponentLevel(component));
            component.draw(g, area.x, y);
            y += ((SlideItem) component).getBoundingBox(g, view, scale, style).height;
        }
    }

    @Override
    public int draw(Graphics g, int x, int y)
    {
        Rectangle area = new Rectangle(x, y, Slide.WIDTH, Slide.HEIGHT);
        this.draw(g, area, null);
        return y;
    }

    private float getScale(Rectangle area)
    {
        return Math.min(((float) area.width) / Slide.WIDTH, ((float) area.height) / Slide.HEIGHT);
    }

    private int getComponentLevel(SlideComponent comp)
    {
        if (comp instanceof SlideItem)
        {
            return ((SlideItem) comp).getLevel();
        }
        return 0;
    }
}
