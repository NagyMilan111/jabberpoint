package jabberpoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * <p>The class for a Bitmap item.</p>
 * <p>Bitmap items have the responsibility to draw themselves.</p>
 */
public class BitmapItem extends SlideItem
{
    protected static final String FILE = "File ";
    protected static final String NOT_FOUND = " not found";

    private final String imageName;
    private BufferedImage bufferedImage;

    // level is equal to item-level; name is the name of the file with the Image
    public BitmapItem(int level, Style style, String name)
    {
        super(level, style);
        this.imageName = name;

        try
        {
            if (this.imageName != null)
            {
                this.bufferedImage = ImageIO.read(new File(this.imageName));
            }
        }
        catch (IOException e)
        {
            System.err.println(FILE + this.imageName + NOT_FOUND);
        }
    }

    // An empty bitmap-item
    public BitmapItem()
    {
        this(0, Style.getStyle(0), null);
    }

    // Give the filename of the image
    public String getName()
    {
        return this.imageName;
    }

    // Give the bounding box of the image
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
    {
        if (this.bufferedImage == null) return new Rectangle(0, 0, 0, 0);

        int width = (int) (this.bufferedImage.getWidth(observer) * scale);
        int height = (int) (this.bufferedImage.getHeight(observer) * scale);
        return new Rectangle((int) (myStyle.indent * scale), 0, width, (int) (myStyle.leading * scale) + height);
    }

    // Draw the image
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
    {
        if (this.bufferedImage == null) return;

        int drawX = x + (int) (myStyle.indent * scale);
        int drawY = y + (int) (myStyle.leading * scale);
        int width = (int) (this.bufferedImage.getWidth(observer) * scale);
        int height = (int) (this.bufferedImage.getHeight(observer) * scale);
        g.drawImage(this.bufferedImage, drawX, drawY, width, height, observer);
    }

    @Override
    public String toString()
    {
        return "BitmapItem[" + this.getLevel() + "," + this.imageName + "]";
    }
}
