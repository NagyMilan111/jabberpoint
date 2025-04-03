//package jabberpoint;
//
//import javax.swing.*;
//import java.io.IOException;
//
///**
// * jabberpoint.JabberPoint Main Programma
// * <p>This program is distributed under the terms of the accompanying
// * COPYRIGHT.txt file (which is NOT the GNU General Public License).
// * Please read it. Your use of the software constitutes acceptance
// * of the terms in the COPYRIGHT.txt file.</p>
// *
// * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
// * @version 1.6 2014/05/16 Sylvia Stuurman
// */
//
//public class JabberPoint
//{
//    protected static final String IOERR = "IO Error: ";
//    protected static final String JABERR = "Jabberpoint Error ";
//    protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";
//
//    /**
//     * Het Main Programma
//     */
//    public static void main(String[] argv)
//    {
//        CommandInvoker invoker = new CommandInvoker();
//        Style.createStyles();
//        Presentation presentation = new Presentation();
//        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
//
//        try
//        {
//            if (argv.length == 0)
//            { //a demo presentation if no arguments
//                Accessor.getDemoAccessor().loadFile(presentation, "");
//            }
//			else
//            {
//                new XMLAccessor().loadFile(presentation, argv[0]); //otherwise load the presentation which is in the argument
//            }
//
//            presentation.setSlideNumber(0);
//        }
//		catch (IOException ex)
//        {
//            JOptionPane.showMessageDialog(null,
//                    IOERR + ex, JABERR,
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}

package jabberpoint;

import jabberpoint.commands.*;

import javax.swing.*;
import java.io.IOException;

public class JabberPoint {

    private static final String IOERR = "IO Error: ";
    private static final String JABERR = "JabberPoint Error";
    private static final String JABVERSION = "JabberPoint 1.6 - OU";

    public static void main(String[] args) {
        // Set OS-native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }

        // 🔧 Initialize styles
        Style.createStyles();

        // Create main objects
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
        CommandInvoker invoker = new CommandInvoker();

        // Register all commands
        invoker.addCommand("Open", new OpenFileCommand(frame));
        invoker.addCommand("Save", new SaveCommand(frame));
        invoker.addCommand("Exit", new ExitApplicationCommand(frame));
        invoker.addCommand("Next", new NextSlideCommand(frame));
        invoker.addCommand("Prev", new PreviousSlideCommand(frame));
        invoker.addCommand("Goto", new GoToSlideCommand(frame));
        invoker.addCommand("New", new NewFileCommand(frame));
        invoker.addCommand("About", new AboutBoxCommand(frame));

        frame.setupControllers(invoker);

        try {
            // 🔄 If you want a demo slide when no file is provided:
            if (args.length == 0) {
                Accessor.getDemoAccessor().loadFile(presentation, "");
            } else {
                new XMLAccessor().loadFile(presentation, args[0]);
            }
            presentation.setSlideNumber(0);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, IOERR + ex.getMessage(), JABERR, JOptionPane.ERROR_MESSAGE);
        }

        // ✅ OPTIONAL: If you want to show a manual welcome slide instead of loading demo file, use this instead:

        Slide welcome = new Slide();
        welcome.setTitle("Welcome to JabberPoint!");
        welcome.append(SlideItemFactory.createSlideItem("text", 1, Style.getStyle(1), "Use File > Open to load a presentation."));
        presentation.append(welcome);
        presentation.setSlideNumber(0);

    }
}