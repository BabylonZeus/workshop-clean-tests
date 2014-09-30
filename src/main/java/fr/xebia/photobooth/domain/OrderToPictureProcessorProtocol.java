package fr.xebia.photobooth.domain;

public class OrderToPictureProcessorProtocol {

    private final PictureProcessorColorimetry pictureProcessorColorimetry = new PictureProcessorColorimetry();
    private final PictureProcessorFormat pictureProcessorFormat = new PictureProcessorFormat();

    public String convert(Order order){
        return order.getColorimetry().accept(pictureProcessorColorimetry) + ";" + order.getFormat().accept(pictureProcessorFormat);
    }
}
