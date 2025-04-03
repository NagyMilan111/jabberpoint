package jabberpoint.commands;

import jabberpoint.AboutBox;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

public class AboutBoxCommand extends SlideViewerFrameController
{
    public AboutBoxCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute()
    {
        AboutBox.show(frame);
    }
}
