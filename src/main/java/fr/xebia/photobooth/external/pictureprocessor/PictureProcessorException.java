package fr.xebia.photobooth.external.pictureprocessor;

public class PictureProcessorException extends Exception {

    public PictureProcessorException(String msg, Exception e) {
        super(msg, e);
    }

    public PictureProcessorException(String msg) {
        super(msg);
    }
}
