package jabberpoint;

import jabberpoint.commands.*;

import javax.swing.*;
import java.io.IOException;

import static jabberpoint.Constants.*;

public class JabberPoint
{


    public static void main(String[] args)
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
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
        CommandInvoker invoker = new CommandInvoker();

        // Register all commands
        invoker.addCommand(OPEN, new OpenFileCommand(frame));
        invoker.addCommand(SAVE, new SaveCommand(frame));
        invoker.addCommand(EXIT, new ExitApplicationCommand(frame));
        invoker.addCommand(NEXT, new NextSlideCommand(frame));
        invoker.addCommand(PREV, new PreviousSlideCommand(frame));
        invoker.addCommand(GOTO, new GoToSlideCommand(frame));
        invoker.addCommand(NEW, new NewFileCommand(frame));
        invoker.addCommand(ABOUT, new AboutBoxCommand(frame));

        frame.setupControllers(invoker);

        try
        {
            // 🔄 If you want a demo slide when no file is provided:
            if (args.length == 0)
            {
                Accessor.getDemoAccessor().loadFile(presentation, "");
            }
            else
            {
                new XMLAccessor().loadFile(presentation, args[0]);
            }
            presentation.setSlideNumber(0);

        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, IOERR + ex.getMessage(), JABERR, JOptionPane.ERROR_MESSAGE);
        }

        Slide welcome = new Slide();
        welcome.setTitle("Welcome to JabberPoint!");
        welcome.append(SlideItemFactory.createSlideItem("text", 1, Style.getStyle(1), "Use File > Open to load a presentation."));
        presentation.append(welcome);
        presentation.setSlideNumber(0);
    }
}
