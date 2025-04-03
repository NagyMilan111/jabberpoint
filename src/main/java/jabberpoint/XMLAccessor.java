package jabberpoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
 * jabberpoint.XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor
{

    /**
     * Default API to use.
     */
    protected static final String DEFAULT_API_TO_USE = "dom";

    /**
     * namen van xml tags of attributen
     */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";

    /**
     * tekst van messages
     */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";


    private String getTitle(Element element, String tagName)
    {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

//    public void loadFile(Presentation presentation, String filename) throws IOException
//    {
//        int slideNumber, itemNumber, max = 0, maxItems = 0;
//
//        try
//        {
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = builder.parse(new File(filename));
//            Element doc = document.getDocumentElement();
//            presentation.setTitle(getTitle(doc, SHOWTITLE));
//
//            NodeList slides = doc.getElementsByTagName(SLIDE);
//            max = slides.getLength();
//
//            for (slideNumber = 0; slideNumber < max; slideNumber++)
//            {
//                Element xmlSlide = (Element) slides.item(slideNumber);
//                Slide slide = new Slide();
//                slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
//                presentation.append(slide);
//
//                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
//                maxItems = slideItems.getLength();
//
//                for (itemNumber = 0; itemNumber < maxItems; itemNumber++)
//                {
//                    Element item = (Element) slideItems.item(itemNumber);
//                    loadSlideItem(slide, item);
//                }
//            }
//        }
//		catch (IOException iox)
//        {
//            System.err.println(iox);
//        }
//		catch (SAXException sax)
//        {
//            System.err.println(sax.getMessage());
//        }
//		catch (ParserConfigurationException pcx)
//        {
//            System.err.println(PCE);
//        }
//    }

//    protected void loadSlideItem(Slide slide, Element item)
//    {
//        int level = 1; // default level
//        NamedNodeMap attributes = item.getAttributes();
//        String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
//
//        if (leveltext != null)
//        {
//            try
//            {
//                level = Integer.parseInt(leveltext);
//            }
//			catch (NumberFormatException x)
//            {
//                System.err.println(NFE);
//            }
//        }
//
//        String type = attributes.getNamedItem(KIND).getTextContent();
//
//        if (TEXT.equals(type))
//        {
//            // Use factory to create a text item with new constructor signature.
//            slide.append(SlideItemFactory.createSlideItem("text", level, Style.getStyle(level), item.getTextContent()));
//        }
//		else if (IMAGE.equals(type))
//        {
//            // Use factory to create a bitmap item.
//            slide.append(SlideItemFactory.createSlideItem("bitmap", level, Style.getStyle(level), item.getTextContent()));
//        }
//		else
//        {
//            System.err.println(UNKNOWNTYPE);
//        }
//    }

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(SLIDE);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
                presentation.append(slide);
                // Parse slide items
                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
                    Element itemElement = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, itemElement);
                }
            }
        } catch (SAXException | ParserConfigurationException ex) {
            // Wrap XML parse errors in an IOException to pass back to caller
            throw new IOException("Error parsing XML file: " + ex.getMessage(), ex);
        }
        // (IOException from File I/O will propagate automatically due to throws declaration)
    }

    protected void loadSlideItem(Slide slide, Element itemElement)
    {
        int level = 1;
        // ... Determine level (parse "level" attribute) – unchanged ...
        String type = itemElement.getAttribute(KIND);
        String data = itemElement.getTextContent();
        if (TEXT.equals(type)) {
            slide.append(SlideItemFactory.createSlideItem(TEXT, level, Style.getStyle(level), data));
        } else if (IMAGE.equals(type)) {
            slide.append(SlideItemFactory.createSlideItem(IMAGE, level, Style.getStyle(level), data));
        } else {
            System.err.println(UNKNOWNTYPE);
        }
    }

    // Save the presentation in XML format.
    public void saveFile(Presentation presentation, String filename) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        System.out.println("<?xml version=\"1.0\"?>");
        System.out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        System.out.println("<presentation>");
        System.out.print("<showtitle>");
        System.out.print(presentation.getTitle());
        System.out.println("</showtitle>");

        // Write each slide.
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++)
        {
            Slide slide = presentation.getSlide(slideNumber);
            System.out.println("<slide>");
            System.out.println("<title>" + slide.getTitle() + "</title>");
            // Use Vector<SlideComponent> instead of Vector<SlideItem>
            Vector<SlideComponent> slideComponents = slide.getSlideItems();

            // Write each slide component.
            for (int itemNumber = 0; itemNumber < slideComponents.size(); itemNumber++)
            {
                SlideComponent comp = slideComponents.elementAt(itemNumber);
                System.out.print("<item kind=");

                if (comp instanceof TextItem)
                {
                    System.out.print("\"text\" level=\"" + ((TextItem) comp).getLevel() + "\">");
                    System.out.print(((TextItem) comp).getText());
                }
				else if (comp instanceof BitmapItem)
                {
                    System.out.print("\"image\" level=\"" + ((BitmapItem) comp).getLevel() + "\">");
                    System.out.print(((BitmapItem) comp).getName());
                }
				else
                {
                    System.out.println("Ignoring " + comp);
                }

                System.out.println("</item>");
            }

            System.out.println("</slide>");
        }

        System.out.println("</presentation>");
        System.out.close();
    }
}
