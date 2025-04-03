package jabberpoint;

import java.io.IOException;

/**
 * <p>Een jabberpoint.Accessor maakt het mogelijk om gegevens voor een presentatie
 * te lezen of te schrijven.</p>
 * <p>Niet-abstracte subklassen moeten de load en de save methode implementeren.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class Accessor
{
    public Accessor()
    {
    }

    public static Accessor getDemoAccessor()
    {
        return new DemoPresentation();
    }

    abstract public void loadFile(Presentation p, String fn) throws IOException;

    abstract public void saveFile(Presentation p, String fn) throws IOException;

}
