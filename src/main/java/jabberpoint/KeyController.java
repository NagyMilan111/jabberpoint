package jabberpoint;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static jabberpoint.Constants.*;

/**
 * <p>This is the jabberpoint.KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter
{
    private final CommandInvoker invoker;

    public KeyController(CommandInvoker invoker)
    {

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
                this.invoker.executeCommand(NEXT);
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                this.invoker.executeCommand(PREV);
                break;
            case 'q':
            case 'Q':
                this.invoker.executeCommand(EXIT);
            default:
                break;
        }
    }
}
