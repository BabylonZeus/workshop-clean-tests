package fr.xebia.photobooth.domain;

public class PictureProcessorFormat implements Format.FormatVisitor {
    @Override
    public String visitPortrait() {
        return "P";
    }
}
