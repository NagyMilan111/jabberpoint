package jabberpoint;

import javax.swing.JFrame;

public abstract class SlideViewerFrameController implements Command
{

    protected SlideViewerFrame frame;

    public SlideViewerFrameController(SlideViewerFrame frame)
    {
     this.frame = frame;
    }

    public JFrame getFrame()
    {
        return this.frame;
    }

    public void setFrame(SlideViewerFrame frame)
    {
        this.frame = frame;
    }
}
