package jabberpoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * XMLAccessor reads and writes XML presentation files.
 */
public class XMLAccessor extends Accessor
{

    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";

    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

    private String getTitle(Element element, String tagName)
    {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(this.getTitle(doc, SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(SLIDE);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++)
            {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(this.getTitle(xmlSlide, SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++)
                {
                    Element itemElement = (Element) slideItems.item(itemNumber);
                    this.loadSlideItem(slide, itemElement);
                }
            }
        }
        catch (SAXException | ParserConfigurationException ex)
        {
            throw new IOException("Error parsing XML file: " + ex.getMessage(), ex);
        }
    }

    protected void loadSlideItem(Slide slide, Element itemElement)
    {
        int level = 1;

        try
        {
            String levelText = itemElement.getAttribute(LEVEL);
            if (!levelText.isEmpty())
            {
                level = Integer.parseInt(levelText);
            }
        }
        catch (NumberFormatException ex)
        {
            System.err.println(NFE + ": " + ex.getMessage());
        }

        String type = itemElement.getAttribute(KIND);
        String data = itemElement.getTextContent();

        if (TEXT.equals(type))
        {
            slide.append(SlideItemFactory.createSlideItem(TEXT, level, Style.getStyle(level), data));
        }
        else if (IMAGE.equals(type))
        {
            slide.append(SlideItemFactory.createSlideItem(IMAGE, level, Style.getStyle(level), data));
        }
        else
        {
            System.err.println(UNKNOWNTYPE + ": " + type);
        }
    }

    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException
    {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename)))
        {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
            out.println("<presentation>");
            out.println("<showtitle>" + presentation.getTitle() + "</showtitle>");

            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++)
            {
                Slide slide = presentation.getSlide(slideNumber);
                out.println("<slide>");
                out.println("<title>" + slide.getTitle() + "</title>");

                Vector<SlideComponent> slideComponents = slide.getSlideItems();
                for (int itemNumber = 0; itemNumber < slideComponents.size(); itemNumber++)
                {
                    SlideComponent comp = slideComponents.elementAt(itemNumber);

                    if (comp instanceof TextItem)
                    {
                        TextItem text = (TextItem) comp;
                        out.print("<item kind=\"text\" level=\"" + text.getLevel() + "\">");
                        out.print(text.getText());
                        out.println("</item>");
                    }
                    else if (comp instanceof BitmapItem)
                    {
                        BitmapItem img = (BitmapItem) comp;
                        out.print("<item kind=\"image\" level=\"" + img.getLevel() + "\">");
                        out.print(img.getName());
                        out.println("</item>");
                    }
                    else
                    {
                        System.err.println("Ignoring unsupported item: " + comp);
                    }
                }

                out.println("</slide>");
            }

            out.println("</presentation>");
        }
    }
}
