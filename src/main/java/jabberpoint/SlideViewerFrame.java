package jabberpoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main application window for JabberPoint.
 */
public class SlideViewerFrame extends JFrame
{

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private static final long serialVersionUID = 3227L;
    private static final String JABTITLE = "JabberPoint - Presentation Viewer";

    private Presentation presentation;

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);
        this.presentation = presentation;

        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(this.presentation, this);
        this.presentation.setShowView(slideViewerComponent);
        this.setupWindow(slideViewerComponent);
    }

    public void setupWindow(SlideViewerComponent slideViewerComponent)
    {
        this.setTitle(JABTITLE);

        // Optional: Set application icon
        try
        {
            ImageIcon icon = new ImageIcon("resources/icon.png");
            this.setIconImage(icon.getImage());
        }
        catch (Exception e)
        {
            System.err.println("Icon not found or failed to load.");
        }

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        this.getContentPane().add(slideViewerComponent);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setVisible(true);
    }

    public void setupControllers(CommandInvoker invoker)
    {
        this.addKeyListener(new KeyController(this, invoker));
        this.setMenuBar(new MenuController(this, invoker));
    }

    public Presentation getPresentation()
    {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }
}
