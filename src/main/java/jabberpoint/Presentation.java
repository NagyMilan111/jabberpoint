package jabberpoint;

import java.util.ArrayList;

/**
 * Maintains the slides in the presentation.
 * There is only one instance of this class per presentation.
 */
public class Presentation
{
    private String showTitle; // title of the presentation
    private ArrayList<Slide> showList; // list of slides
    private int currentSlideNumber = 0; // current slide number
    private SlideViewerComponent slideViewComponent = null; // slide view component

    public Presentation()
    {
        this.slideViewComponent = null;
        this.clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        this.clear();
    }

    // Get the number of slides in the presentation
    public int getSize()
    {
        return this.showList.size();
    }

    public String getTitle()
    {
        return this.showTitle;
    }

    public void setTitle(String title)
    {
        this.showTitle = title;
    }

    public void setShowView(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
    }

    public int getSlideNumber()
    {
        return this.currentSlideNumber;
    }

    public void setSlideNumber(int number)
    {
        this.currentSlideNumber = number;

        if (this.slideViewComponent != null)
        {
            this.slideViewComponent.update(this, this.getCurrentSlide());
        }
    }

    // Go to previous slide (if not at the beginning)
    public void prevSlide()
    {
        if (this.currentSlideNumber > 0)
        {
            this.setSlideNumber(this.currentSlideNumber - 1);
        }
    }

    // Go to next slide (if not at the end)
    public void nextSlide()
    {
        if (this.currentSlideNumber < (this.getSize() - 1))
        {
            this.setSlideNumber(this.currentSlideNumber + 1);
        }
    }

    // Reset the presentation
    public void clear()
    {
        this.showList = new ArrayList<>();
        this.setSlideNumber(0);
    }

    public void append(Slide slide)
    {
        this.showList.add(slide);
    }

    public Slide getSlide(int number)
    {
        if (number < 0 || number >= this.getSize())
        {
            return null; // Consider logging or throwing exception here
        }

        return this.showList.get(number);
    }

    public Slide getCurrentSlide()
    {
        return this.getSlide(this.currentSlideNumber);
    }

    public void exit(int statusCode)
    {
        System.exit(statusCode);
    }
}
