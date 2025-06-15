package jabberpoint.commands;

import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class ExitApplicationCommand extends SlideViewerFrameController
{

    public ExitApplicationCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute()
    {
        this.frame.getPresentation().exit(0);
    }
}
