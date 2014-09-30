package fr.xebia.photobooth.domain;

import java.math.BigDecimal;


public class Order {
    private Format format;
    private Colorimetry colorimetry;

    private BigDecimal money;
    private String stringFile;

    public Order(){

    }

    public Order(Colorimetry colorimetry, Format format, BigDecimal money) {
        this.colorimetry = colorimetry;
        this.format = format;
        this.money = money;
    }

    public Order(String colorimetry, String format, String money, String stringFile) {
        this.colorimetry = Colorimetry.valueOf(colorimetry);
        this.format = Format.valueOf(format);
        this.money = new BigDecimal(money);
        this.stringFile = stringFile;
    }

    public Format getFormat() {
        return format;
    }

    public Colorimetry getColorimetry() {
        return colorimetry;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getStringFile() {
        return stringFile;
    }

}
