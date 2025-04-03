package jabberpoint;

import jabberpoint.commands.ExitApplicationCommand;
import jabberpoint.commands.NextSlideCommand;
import jabberpoint.commands.PreviousSlideCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static jabberpoint.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        } catch (IOException exception)
        {
            System.out.println("Something went wrong while trying to load the demo");
        }

        this.invoker = new CommandInvoker();
        this.frame = new SlideViewerFrame(JABVERSION, this.presentation);
        this.invoker.addCommand(NEXT, new NextSlideCommand(this.frame));
        this.invoker.addCommand(PREV, new PreviousSlideCommand(this.frame));
        this.invoker.addCommand(EXIT, new ExitApplicationCommand(this.frame));
        this.frame.setupControllers(this.invoker);


    }

    @Test
    void nextSlideTestFromSlide0ShouldGoToTheNextSlide()
    {
        this.presentation.setSlideNumber(0);
        this.invoker.executeCommand(NEXT);

        assertEquals(1, this.presentation.getSlideNumber());
    }

    @Test
    void nextSlideTestFromSlideBeforeTheLastShouldGoToTheNext()
    {
        this.presentation.setSlideNumber(this.presentation.getSize() - 2);
        this.invoker.executeCommand(NEXT);

        assertEquals(this.presentation.getSize() - 1, this.presentation.getSlideNumber());
    }

    @Test
    void nextSlideTestFromLastSlideShouldNotChange()
    {
        this.presentation.setSlideNumber(this.presentation.getSize() - 1);
        this.invoker.executeCommand(NEXT);

        assertEquals(this.presentation.getSize() - 1, this.presentation.getSlideNumber());
    }

    @Test
    void previousSlideTestFrom0ShouldNotChange()
    {
        this.presentation.setSlideNumber(0);
        this.invoker.executeCommand(NEXT);

        assertEquals(0, this.presentation.getSlideNumber());
    }
}