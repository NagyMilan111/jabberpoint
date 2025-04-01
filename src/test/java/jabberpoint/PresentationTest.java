package jabberpoint;

import jabberpoint.commands.ExitApplicationCommand;
import jabberpoint.commands.NextSlideCommand;
import jabberpoint.commands.PreviousSlideCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PresentationTest
{
    private CommandInvoker invoker;
    private Presentation presentation;
    private SlideViewerFrame frame;


    @BeforeEach
    void setup()
    {
        Style.createStyles();
        this.presentation = new Presentation();

        try
        {
            Accessor.getDemoAccessor().loadFile(this.presentation, "");

        }
        catch (IOException exception)
        {
            System.out.println("Something went wrong while trying to load the demo");
        }

        this.invoker = new CommandInvoker();
        this.frame = new SlideViewerFrame("TEST VERSION", this.presentation);
        this.invoker.addCommand("Next", new NextSlideCommand(this.frame));
        this.invoker.addCommand("Prev", new PreviousSlideCommand(this.frame));
        this.invoker.addCommand("Exit", new ExitApplicationCommand(this.frame));
        this.frame.setupControllers(this.invoker);


    }

    @Test
    void nextSlideTestFromSlide0ShouldGoToTheNextSlide()
    {
        this.presentation.setSlideNumber(0);
        this.invoker.executeCommand("Next", null);

        assertEquals(1, this.presentation.getSlideNumber());
    }

    @Test
    void nextSlideTestFromSlideBeforeTheLastShouldGoToTheNext()
    {
        this.presentation.setSlideNumber(this.presentation.getSize() - 2);
        this.invoker.executeCommand("Next", null);

        assertEquals(this.presentation.getSize() - 1, this.presentation.getSlideNumber());
    }

    @Test
    void nextSlideTestFromLastSlideShouldNotChange()
    {
        this.presentation.setSlideNumber(this.presentation.getSize() - 1);
        this.invoker.executeCommand("Next", null);

        assertEquals(this.presentation.getSize() - 1, this.presentation.getSlideNumber());
    }

    @Test
    void previousSlideTestFrom0ShouldNotChange()
    {
        this.presentation.setSlideNumber(0);
        this.invoker.executeCommand("Prev", null);

        assertEquals(0, this.presentation.getSlideNumber());
    }
}