package jabberpoint;

import javax.swing.*;
import java.awt.*;

import static jabberpoint.Constants.*;

/**
 * SlideViewerComponent is a graphical component that can show slides.
 */
public class SlideViewerComponent extends JComponent
{

    private Slide slide; // current slide
    private Presentation presentation;
    private JFrame frame;

    public SlideViewerComponent(Presentation pres, JFrame frame)
    {
        this.setBackground(BGCOLOR);
        this.presentation = pres;
        this.frame = frame;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(SLIDEWIDTH, SLIDEHEIGHT);
    }

    public void update(Presentation presentation, Slide data)
    {
        if (data == null)
        {
            this.repaint();
            return;
        }

        this.presentation = presentation;
        this.slide = data;
        this.repaint();
        this.frame.setTitle(this.presentation.getTitle());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);

        if (this.presentation.getSlideNumber() < 0 || this.slide == null)
        {
            return;
        }

        Style pageStyle = Style.getStyle(1);
        g.setFont(pageStyle.getFont());
        g.setColor(pageStyle.color);

        String slideText = "Slide " + (this.presentation.getSlideNumber() + 1) + " of " + this.presentation.getSize();
        FontMetrics metrics = g.getFontMetrics();
        int x = this.getWidth() - metrics.stringWidth(slideText) - 20;
        int y = this.getHeight() - 10;
        g.drawString(slideText, x, y);

        Rectangle area = new Rectangle(0, 20, this.getWidth(), this.getHeight() - 20);
        this.slide.draw(g, area, this);
    }
}
