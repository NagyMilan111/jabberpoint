package jabberpoint.commands;

import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class NewFileCommand extends SlideViewerFrameController
{
    public NewFileCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute()
    {
        this.frame.getPresentation().clear();
        this.frame.repaint();
    }
}
