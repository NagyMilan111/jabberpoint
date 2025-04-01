package jabberpoint;

import jabberpoint.commands.ExitApplicationCommand;
import jabberpoint.commands.NextSlideCommand;
import jabberpoint.commands.PreviousSlideCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class KeyControllerTest
{
    private KeyController keyController;
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
        this.keyController = new KeyController(this.frame, this.invoker);


    }

    @Test
    void keyControllerTestPrevSlideShouldAcceptInput()
    {
        this.presentation.setSlideNumber(1);
        KeyEvent key = new KeyEvent(this.frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_UP, KeyEvent.CHAR_UNDEFINED);
        this.keyController.keyPressed(key);

        assertEquals(0, this.presentation.getSlideNumber());
    }

    @Test
    void keyControllerTestNextSlideShouldAcceptInput()
    {
        this.presentation.setSlideNumber(0);
        KeyEvent key = new KeyEvent(this.frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_PAGE_DOWN, KeyEvent.CHAR_UNDEFINED);
        this.keyController.keyPressed(key);

        assertEquals(1, this.presentation.getSlideNumber());
    }

    @Test
    void keyControllerTestUnknownKeyShouldNotDoAnything()
    {
        this.presentation.setSlideNumber(0);
        KeyEvent key = new KeyEvent(this.frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, KeyEvent.CHAR_UNDEFINED);
        this.keyController.keyPressed(key);

        assertEquals(0, this.presentation.getSlideNumber());
    }

}