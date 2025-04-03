package jabberpoint.commands;

import jabberpoint.Accessor;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;
import jabberpoint.XMLAccessor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static jabberpoint.MenuController.*;

public class OpenFileCommand extends SlideViewerFrameController
{
    public OpenFileCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

//    @Override
//    public void execute(Integer slideNumber)
//    {
//        this.frame.getPresentation().clear();
//        Accessor xmlAccessor = new XMLAccessor();
//
//        try
//        {
//            xmlAccessor.loadFile(this.frame.getPresentation(), TESTFILE); //This will only ever load the test file
//            this.frame.getPresentation().setSlideNumber(0);
//        } catch (IOException exc)
//        {
//            JOptionPane.showMessageDialog(this.frame, IOEX + exc,
//                    LOADERR, JOptionPane.ERROR_MESSAGE);
//        }
//
//        this.frame.repaint();
//    }

    @Override
    public void execute(Integer slideNumber) {
        // Let the user choose an XML file to open
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Clear current presentation and load the selected file
            frame.getPresentation().clear();
            Accessor xmlAccessor = new XMLAccessor();
            try {
                xmlAccessor.loadFile(frame.getPresentation(), file.getAbsolutePath());
                frame.getPresentation().setSlideNumber(0);
            } catch (IOException exc) {
                // Show an error dialog if loading fails
                JOptionPane.showMessageDialog(frame,
                        IOEX + exc.getMessage(), LOADERR, JOptionPane.ERROR_MESSAGE);
            }
            frame.repaint();
        }
    }
}
