package jabberpoint;

import jabberpoint.commands.AboutBoxCommand;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar
{

    private SlideViewerFrame parent; // the frame, only used as parent for the Dialogs

    private static final long serialVersionUID = 227L;

    private CommandInvoker invoker;

    public static final String ABOUT = "About";
    public static final String FILE = "File";
    public static final String EXIT = "Exit";
    public static final String GOTO = "Go to";
    public static final String HELP = "Help";
    public static final String NEW = "New";
    public static final String NEXT = "Next";
    public static final String OPEN = "Open";
    public static final String PAGENR = "Page number?";
    public static final String PREV = "Prev";
    public static final String SAVE = "Save";
    public static final String VIEW = "View";

    public static final String TESTFILE = "test.xml";
    public static final String SAVEFILE = "dump.xml";

    public static final String IOEX = "IO Exception: ";
    public static final String LOADERR = "Load Error";
    public static final String SAVEERR = "Save Error";

    public MenuController(SlideViewerFrame frame, CommandInvoker invoker)
    {
        parent = frame;
        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);
        this.invoker = invoker;

        //Open a new presentation
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                invoker.executeCommand(OPEN, null);
            }
        });

        //Create a new blank presentation
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                invoker.executeCommand(NEW, null);
            }
        });

        //Save presentation to file
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
				invoker.executeCommand(SAVE, null);

            }
        });

        //Exit Jabberpoint
        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
				invoker.executeCommand(EXIT, null);

			}
        });
        add(fileMenu);

        //Next slide
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
				invoker.executeCommand(NEXT, null);

			}
        });

        //Previous slide
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
				invoker.executeCommand(PREV, null);
			}
        });

        //Go to the entered slide number
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
				invoker.executeCommand(GOTO, null);
            }
        });
        add(viewMenu);

        //Open about box
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                invoker.executeCommand(ABOUT, null);
            }
        });
        setHelpMenu(helpMenu);        // needed for portability (Motif, etc.).
    }

    // create a menu item
    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
