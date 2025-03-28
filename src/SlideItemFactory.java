public class SlideItemFactory
{
    public static SlideItem createSlideItem(String type, int level, Style style, String content) {
        switch (type.toLowerCase()) {
            case "text":
                return new TextItem(level, style, content);
            case "bitmap":
            case "image":
                return new BitmapItem(level, style, content);
            default:
                throw new IllegalArgumentException("Unknown slide item type: " + type);
        }
    }
}
