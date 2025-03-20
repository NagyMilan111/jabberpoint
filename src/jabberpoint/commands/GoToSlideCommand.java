package jabberpoint.commands;

import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class GoToSlideCommand extends SlideViewerFrameController
{

    public GoToSlideCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute(Integer slideNumber)
    {
        this.frame.getPresentation().setSlideNumber(slideNumber);
    }
}
