package jabberpoint.commands;

import jabberpoint.Accessor;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;
import jabberpoint.XMLAccessor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static jabberpoint.Constants.IOEX;
import static jabberpoint.Constants.LOADERR;

public class OpenFileCommand extends SlideViewerFrameController
{
    public OpenFileCommand(SlideViewerFrame frame)
    {
        super(frame);
    }

    @Override
    public void execute() {
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
