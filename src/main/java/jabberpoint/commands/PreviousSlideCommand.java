package jabberpoint.commands;

import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class PreviousSlideCommand extends SlideViewerFrameController
{
    public PreviousSlideCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute(Integer slideNumber)
    {
        this.frame.getPresentation().prevSlide();
    }
}
