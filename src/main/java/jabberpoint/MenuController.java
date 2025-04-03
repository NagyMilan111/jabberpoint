package jabberpoint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jabberpoint.Constants.*;

public class MenuController extends MenuBar
{



    private static final long serialVersionUID = 227L;

    private final SlideViewerFrame parent;
    private final CommandInvoker invoker;

    public MenuController(SlideViewerFrame frame, CommandInvoker invoker)
    {
        this.parent = frame;
        this.invoker = invoker;

        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);

        // Open a new presentation
        fileMenu.add(menuItem = this.mkMenuItem(OPEN));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(OPEN, null);
            }
        });

        // Create a new blank presentation
        fileMenu.add(menuItem = this.mkMenuItem(NEW));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(NEW, null);
            }
        });

        // Save presentation to file
        fileMenu.add(menuItem = this.mkMenuItem(SAVE));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MenuController.this.invoker.executeCommand(SAVE, null);
            }
        });

        // Exit Jabberpoint
        fileMenu.addSeparator();
        fileMenu.add(menuItem = this.mkMenuItem(EXIT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(EXIT, null);
            }
        });

        this.add(fileMenu);

        // View menu
        Menu viewMenu = new Menu(VIEW);

        // Next slide
        viewMenu.add(menuItem = this.mkMenuItem(NEXT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(NEXT, null);
            }
        });

        // Previous slide
        viewMenu.add(menuItem = this.mkMenuItem(PREV));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(PREV, null);
            }
        });

        // Go to slide
        viewMenu.add(menuItem = this.mkMenuItem(GOTO));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(GOTO, null);
            }
        });

        this.add(viewMenu);

        // Help menu
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = this.mkMenuItem(ABOUT));
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                MenuController.this.invoker.executeCommand(ABOUT, null);
            }
        });

        this.setHelpMenu(helpMenu);
    }

    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
