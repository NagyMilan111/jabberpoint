public class SlideItemFactory
{
    public static SlideItem createSlideItem(String type, int level, Style style, String content) {
        if (type == null) {
            throw new IllegalArgumentException("Slide item type cannot be null");
        }
        switch (type.toLowerCase()) {
            case "text":
            case "txt":
                return new TextItem(level,content);
            case "image":
            case "img":
            case "bitmap":
                return new BitmapItem(level, content);
            default:
                throw new IllegalArgumentException("Unknown slide item type: " + type);
        }
    }
}
