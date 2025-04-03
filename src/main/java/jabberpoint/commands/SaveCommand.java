package jabberpoint.commands;

import jabberpoint.Accessor;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;
import jabberpoint.XMLAccessor;

import javax.swing.*;
import java.io.IOException;

import static jabberpoint.MenuController.*;

public class SaveCommand extends SlideViewerFrameController
{

    public SaveCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute(Integer slideNumber)
    {
        Accessor xmlAccessor = new XMLAccessor();
        try
        {
            xmlAccessor.saveFile(this.frame.getPresentation(), SAVEFILE);
        } catch (IOException exc)
        {
            JOptionPane.showMessageDialog(this.frame, IOEX + exc,
                    SAVEERR, JOptionPane.ERROR_MESSAGE);
        }
    }
}
