import java.util.ArrayList;
import java.util.List;

/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>In the revised design, each Slide is a composite (implementing SlideComponent)
 * and is built using the Composite and Factory Method patterns.</p>
 * <p>There is only one instance of this class.</p>
 *
 * (Original code by Darwin, Florijn & Sylvia Stuurman, updated for Composite/Factory.)
 */
public class Presentation {
	private String showTitle;               // Title of the presentation.
	private List<Slide> showList;           // A list of Slides (composites).
	private int currentSlideNumber = 0;     // The slide number of the current Slide.
	private SlideViewerComponent slideViewComponent = null;  // The view component for displaying slides.

	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	// Returns the number of slides.
	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	// Returns the number of the current slide.
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// Changes the current slide number and notifies the view component.
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	// Go to the previous slide.
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	// Go to the next slide.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size() - 1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Clears the presentation to prepare for a new one.
	void clear() {
		showList = new ArrayList<>();
		setSlideNumber(0);
	}

	// Adds a Slide (composite) to the presentation.
	public void append(Slide slide) {
		showList.add(slide);
	}

	// Returns the slide at the specified index.
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null; // In a full implementation, consider throwing an exception.
		}
		return showList.get(number);
	}

	// Returns the current slide.
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
