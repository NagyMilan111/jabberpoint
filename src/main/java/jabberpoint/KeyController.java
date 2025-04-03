package jabberpoint;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static jabberpoint.MenuController.*;

/**
 * <p>This is the jabberpoint.KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter
{
    private final SlideViewerFrame frame; //This is probably supposed to be a singleton, but isn't
    private final CommandInvoker invoker;

    public KeyController(SlideViewerFrame frame, CommandInvoker invoker)
    {
        this.frame = frame;
        this.invoker = invoker;
    }

    //there are undocumented key combinations here? why? probably a bug
    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case '+':
                invoker.executeCommand(NEXT, null);
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                invoker.executeCommand(PREV, null);
                break;
            case 'q':
            case 'Q':
                invoker.executeCommand(EXIT, null);
            default:
                break;
        }
    }
}
