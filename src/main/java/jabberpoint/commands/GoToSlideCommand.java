//package jabberpoint.commands;
//
//import jabberpoint.SlideViewerFrame;
//import jabberpoint.SlideViewerFrameController;
//
//public class GoToSlideCommand extends SlideViewerFrameController
//{
//
//    public GoToSlideCommand(SlideViewerFrame frame)
//    {
//        super(frame);
//    }
//
//    @Override
//    public void execute(Integer slideNumber)
//    {
//        this.frame.getPresentation().setSlideNumber(slideNumber);
//    }
//}

//package jabberpoint.commands;
//
//import jabberpoint.SlideViewerFrame;
//import jabberpoint.SlideViewerFrameController;
//
//public class GoToSlideCommand extends SlideViewerFrameController
//{
//
//    public GoToSlideCommand(SlideViewerFrame frame)
//    {
//        super(frame);
//    }
//
//    @Override
//    public void execute(Integer slideNumber)
//    {
//        this.frame.getPresentation().setSlideNumber(slideNumber);
//    }
//}

package jabberpoint.commands;

import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;
import jabberpoint.SlideViewerFrameController;

import javax.swing.*;

public class GoToSlideCommand extends SlideViewerFrameController {

    public GoToSlideCommand(SlideViewerFrame frame) {
        super(frame);
    }

    @Override
    public void execute(Integer unused) {
        Presentation presentation = frame.getPresentation();
        String input = JOptionPane.showInputDialog(frame, "Enter slide number:");

        if (input != null) {
            try {
                int slideNumber = Integer.parseInt(input) - 1; // User enters 1-based number
                if (slideNumber >= 0 && slideNumber < presentation.getSize()) {
                    presentation.setSlideNumber(slideNumber);
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid slide number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
