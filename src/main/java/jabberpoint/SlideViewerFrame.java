package jabberpoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main application window for JabberPoint.
 */
public class SlideViewerFrame extends JFrame {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private static final long serialVersionUID = 3227L;
    private static final String JABTITLE = "JabberPoint - Presentation Viewer";
    private Presentation presentation;

    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        this.presentation = presentation;
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        presentation.setShowView(slideViewerComponent);
        setupWindow(slideViewerComponent);
    }

    public void setupWindow(SlideViewerComponent slideViewerComponent) {
        setTitle(JABTITLE);

        // Optional: Set application icon (icon must exist)
        try {
            ImageIcon icon = new ImageIcon("resources/icon.png"); // path to your icon
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Icon not found or failed to load.");
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        getContentPane().add(slideViewerComponent);
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    public void setupControllers(CommandInvoker invoker) {
        addKeyListener(new KeyController(this, invoker));
        setMenuBar(new MenuController(this, invoker));
    }

    public Presentation getPresentation() {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }
}
