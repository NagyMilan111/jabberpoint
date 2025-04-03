package jabberpoint.commands;

import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class NextSlideCommand extends SlideViewerFrameController
{
    public NextSlideCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute()
    {
        frame.getPresentation().nextSlide();
    }
}
