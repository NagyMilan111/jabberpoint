package jabberpoint;

import jabberpoint.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static jabberpoint.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest
{

    private MenuController menuController;
    private CommandInvoker invoker;
    private Presentation presentation;
    private SlideViewerFrame frame;

    @BeforeEach
    void setup()
    {
        // Set OS-native look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }

        // 🔧 Initialize styles
        Style.createStyles();

        // Create main objects
        this.presentation = new Presentation();
        this.frame = new SlideViewerFrame(JABVERSION, this.presentation);
        this.invoker = new CommandInvoker();

        // Register all commands
        this.invoker.addCommand("Open", new OpenFileCommand(this.frame));
        this.invoker.addCommand("Save", new SaveCommand(this.frame));
        this.invoker.addCommand("Exit", new ExitApplicationCommand(this.frame));
        this.invoker.addCommand("Next", new NextSlideCommand(this.frame));
        this.invoker.addCommand("Prev", new PreviousSlideCommand(this.frame));
        this.invoker.addCommand("Goto", new GoToSlideCommand(this.frame));
        this.invoker.addCommand("New", new NewFileCommand(this.frame));
        this.invoker.addCommand("About", new AboutBoxCommand(this.frame));

        this.frame.setupControllers(this.invoker);

        try
        {
            Accessor.getDemoAccessor().loadFile(presentation, "");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, IOERR + ex.getMessage(), JABERR, JOptionPane.ERROR_MESSAGE);
        }

        Slide welcome = new Slide();
        welcome.setTitle("Welcome to JabberPoint!");
        welcome.append(SlideItemFactory.createSlideItem("text", 1, Style.getStyle(1), "Use File > Open to load a presentation."));
        this.presentation.append(welcome);
        this.presentation.setSlideNumber(0);

        this.menuController = new MenuController(this.frame, this.invoker);
    }

    private class TestCommand implements Command
    {
        boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }
    }
    private MenuItem findMenuItem(MenuBar menuBar, String command) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            Menu menu = menuBar.getMenu(i);
            for (int j = 0; j < menu.getItemCount(); j++) {
                MenuItem item = menu.getItem(j);
                if (item != null && item.getLabel().equals(command)) {
                    return item;
                }
            }
        }
        throw new IllegalArgumentException("MenuItem not found: " + command);
    }


    @Test
    void shouldExecuteNextSlideCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(NEXT, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, NEXT);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, NEXT));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecutePrevSlideCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(PREV, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, PREV);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, PREV));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteOpenFileCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(OPEN, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, OPEN);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, OPEN));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteNewFileCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(NEW, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, NEW);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, NEW));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteSaveCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(SAVE, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, SAVE);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, SAVE));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteExitCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(EXIT, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, EXIT);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, EXIT));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteGoToSlideCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(GOTO, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, GOTO);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, GOTO));

        assertTrue(testCommand.executed);
    }

    @Test
    void shouldExecuteAboutCommand() {
        TestCommand testCommand = new TestCommand();
        this.invoker.addCommand(ABOUT, testCommand);

        MenuItem nextSlideMenuItem = findMenuItem(this.menuController, ABOUT);
        nextSlideMenuItem.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ABOUT));

        assertTrue(testCommand.executed);
    }

}