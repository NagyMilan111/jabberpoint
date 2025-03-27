package jabberpoint.commands;

import jabberpoint.Accessor;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;
import jabberpoint.XMLAccessor;

import javax.swing.*;
import java.io.IOException;

import static jabberpoint.MenuController.*;

public class OpenFileCommand extends SlideViewerFrameController
{
    public OpenFileCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute(Integer slideNumber)
    {
        this.frame.getPresentation().clear();
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.loadFile(this.frame.getPresentation(), TESTFILE); //This will only ever load the test file
            this.frame.getPresentation().setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this.frame, IOEX + exc,
                    LOADERR, JOptionPane.ERROR_MESSAGE);
        }
        this.frame.repaint();
    }
}
