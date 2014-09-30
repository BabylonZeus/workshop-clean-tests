package fr.xebia.photobooth.api;

import fr.xebia.photobooth.domain.MachineException;
import fr.xebia.photobooth.domain.Order;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.FileAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.fail;

public class PhotoResourceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PhotoResource photoResource;

    @Before
    public void init() {
        photoResource = new PhotoResource();
    }

    @Test
    public void should_return_hello_world_string() {
        String output = photoResource.getHelloWorld();

        Assertions.assertThat(output).isEqualTo("Hello world");
    }

    @Test
    public void should_save_to_file_base_64_string() throws IOException, MachineException {
        String base64String = "" +
                "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAAL" +
                "EwEAmpwYAAAKwWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjarZZ3UJPZGsbf" +
                "7/vSSKElhCIl9CZIJ4D0GkBBOtgICZBACCEFAbuyqOBaUBEBdUVXRBRcCyBrQUSx" +
                "Itj7giwqyrqoiw2V+wdLuHfuvX/cmfvOnJnfPHPOc973nH8eAOpjrkQiQtUBcsRy" +
                "aUxoACspOYVFfAoI6AMZqMDm8mQS/+joSPjPhQB8uAsIAMAtO65EIoL/rTT46TIe" +
                "ABINAGl8GS8HADkOgHTwJFI5AFYAAKaL5BI5AFYDAAxpUnIKAHYEABiZE9wJAIy0" +
                "Cb4PAAxpXEwgADYMQKJyudJMAMonAGDl8zLlAFQdAHAQ84ViAGoYAPjwBFw+AHUt" +
                "AEzPycnlA1CPA4BV2j/5ZP6LZ5rSk8vNVPLELAAAQAoSyiQibiH8vytHpJi8wxgA" +
                "qAJpWAwAWAAgNdm5EUoWp82OmmQhH2CSBYqw+EnmyQJTJpnPDYqYZEV2vP8kc6VT" +
                "Z4VyTtwkS3NjlP5i0exIpX86R8npsuDYSc4QhnAmuUgQlzjJ+cKE2ZMsy46NmNoT" +
                "qNSlihhlzxnSEOWMObKp3njcqbvkgrgw5VzpQcHKfsTxyj0SeYDSRyKKnupZFKrU" +
                "ZfmxyrNyaZxSz+KGR0/5RCvfBOJAAAoQAx/SQQppkAsikAMLgkAIMpCACLhQCCBP" +
                "L5ADAATmSgqlwkyBnOUvkYjSWRwxz346y8nB0QUgKTmFNfHN75mAAADCvDKl5bUD" +
                "eJQCIJlTGtcU4ORzAPqHKc30HQB1E8DpHp5Cmj+h4QAA8EAGNWCALhiCKViBHTiB" +
                "G3iBHwRDOERBHCTDAuCBAHJACotgCayEEiiDTbANqmA37IUDcBiOQgucgnNwEa5C" +
                "D9yBR9AHg/AaRuADjCEIQkRoCB3RRYwQc8QWcULYiA8SjEQiMUgykopkImJEgSxB" +
                "ViNlSDlShexB6pFfkJPIOeQy0os8QPqRIeQd8gXFUCrKQA1QC3QGykb90Qg0Dp2P" +
                "ZqJ5aBFajG5AK9Fa9BDajJ5Dr6J30D70NTqKAUbBmJgxZoexsUAsCkvBMjAptgwr" +
                "xSqwWqwRa8O6sFtYHzaMfcYRcHQcC2eH88KF4eJxPFwebhluPa4KdwDXjOvE3cL1" +
                "40Zw3/E0vD7eFu+J5+CT8Jn4RfgSfAV+P/4E/gL+Dn4Q/4FAIDAJlgR3QhghmZBF" +
                "WExYT9hJaCK0E3oJA4RRIpGoS7QlehOjiFyinFhC3EE8RDxLvEkcJH4iUUhGJCdS" +
                "CCmFJCatIlWQDpLOkG6SXpDGVNRVzFU8VaJU+CqFKhtV9qm0qdxQGVQZI2uQLcne" +
                "5DhyFnkluZLcSL5Afkx+T6FQTCgelDkUIWUFpZJyhHKJ0k/5TNWk2lADqfOoCuoG" +
                "ah21nfqA+p5Go1nQ/GgpNDltA62edp72lPZJla5qr8pR5asuV61WbVa9qfpGTUXN" +
                "XM1fbYFakVqF2jG1G2rD6irqFuqB6lz1ZerV6ifV76mPatA1HDWiNHI01msc1Lis" +
                "8VKTqGmhGazJ1yzW3Kt5XnOAjtFN6YF0Hn01fR/9An2QQWBYMjiMLEYZ4zCjmzGi" +
                "panlopWgVaBVrXVaq4+JMS2YHKaIuZF5lHmX+UXbQNtfO117nXaj9k3tjzrTdPx0" +
                "0nVKdZp07uh80WXpButm627WbdF9oofTs9Gbo7dIb5feBb3haYxpXtN400qnHZ32" +
                "UB/Vt9GP0V+sv1f/mv6ogaFBqIHEYIfBeYNhQ6ahn2GW4VbDM4ZDRnQjHyOh0Vaj" +
                "s0avWFosf5aIVcnqZI0Y6xuHGSuM9xh3G4+ZWJrEm6wyaTJ5Yko2ZZtmmG417TAd" +
                "MTMym2W2xKzB7KG5ijnbXGC+3bzL/KOFpUWixRqLFouXljqWHMsiywbLx1Y0K1+r" +
                "PKtaq9vWBGu2dbb1TuseG9TG1UZgU21zwxa1dbMV2u607Z2On+4xXTy9dvo9O6qd" +
                "v12+XYNdvz3TPtJ+lX2L/ZsZZjNSZmye0TXju4Org8hhn8MjR03HcMdVjm2O75xs" +
                "nHhO1U63nWnOIc7LnVud37rYuqS77HK570p3neW6xrXD9Zubu5vUrdFtyN3MPdW9" +
                "xv0em8GOZq9nX/LAewR4LPc45fHZ081T7nnU808vO69sr4NeL2dazkyfuW/mgLeJ" +
                "N9d7j3efD8sn1ecnnz5fY1+ub63vMz9TP77ffr8X/tb+Wf6H/N8EOARIA04EfAz0" +
                "DFwa2B6EBYUGlQZ1B2sGxwdXBT8NMQnJDGkIGQl1DV0c2h6GD4sI2xx2j2PA4XHq" +
                "OSPh7uFLwzsjqBGxEVURzyJtIqWRbbPQWeGztsx6PNt8tnh2SxREcaK2RD2JtozO" +
                "i/51DmFO9JzqOc9jHGOWxHTF0mMXxh6M/RAXELcx7lG8VbwiviNBLWFeQn3Cx8Sg" +
                "xPLEvqQZSUuTribrJQuTW1OIKQkp+1NG5wbP3TZ3cJ7rvJJ5d+dbzi+Yf3mB3gLR" +
                "gtML1RZyFx5Lxacmph5M/cqN4tZyR9M4aTVpI7xA3nbea74ffyt/KN07vTz9RYZ3" +
                "RnnGy0zvzC2ZQwJfQYVgWBgorBK+zQrL2p31MTsquy57XJQoasoh5aTmnBRrirPF" +
                "nbmGuQW5vRJbSYmkL88zb1veiDRCul+GyObLWuUMuUR+TWGl+EHRn++TX53/aVHC" +
                "omMFGgXigmuFNoXrCl8UhRT9vBi3mLe4Y4nxkpVL+pf6L92zDFmWtqxjueny4uWD" +
                "K0JXHFhJXpm98voqh1Xlq/5anbi6rdigeEXxwA+hPzSUqJZIS+6t8Vqzey1urXBt" +
                "9zrndTvWfS/ll14pcyirKPu6nrf+yo+OP1b+OL4hY0P3RreNuzYRNok33d3su/lA" +
                "uUZ5UfnAlllbmreytpZu/Wvbwm2XK1wqdm8nb1ds76uMrGzdYbZj046vVYKqO9UB" +
                "1U01+jXraj7u5O+8uctvV+Nug91lu7/8JPzp/p7QPc21FrUVewl78/c+35ewr+tn" +
                "9s/1+/X2l+3/Vieu6zsQc6Cz3r2+/qD+wY0NaIOiYejQvEM9h4MOtzbaNe5pYjaV" +
                "HYEjiiOvfkn95e7RiKMdx9jHGo+bH685QT9R2ow0FzaPtAha+lqTW3tPhp/saPNq" +
                "O/Gr/a91p4xPVZ/WOr3xDPlM8Znxs0VnR9sl7cPnMs8NdCzseHQ+6fztzjmd3Rci" +
                "Lly6GHLxfJd/19lL3pdOXfa8fPIK+0rLVberzddcr5247nr9RLdbd/MN9xutPR49" +
                "bb0ze8/c9L157lbQrYu3Obev3pl9p/du/N379+bd67vPv//ygejB24f5D8cerXiM" +
                "f1z6RP1JxVP9p7W/Wf/W1OfWd7o/qP/as9hnjwZ4A69/l/3+dbD4Oe15xQujF/Uv" +
                "nV6eGgoZ6nk199Xga8nrseGSPzT+qHlj9eb4n35/XhtJGhl8K307/m79e933dX+5" +
                "/NUxGj369EPOh7GPpZ90Px34zP7c9SXxy4uxRV+JXyu/WX9r+x7x/fF4zvi4hCvl" +
                "AgAABgBoRgbAuzoAWjIAvQeArDqRk//O98hU0v9vPJGlAQDADaDODyB+BUBkO8Cu" +
                "dgDzFQDUdoBoAIjzA9TZWbn+LlmGs9OEF1UKgP80Pv7eAIDYBvBNOj4+tnN8/Ns+" +
                "AOwBQHveRD4HACCoA5RbauOQmuuma/4tJ/8DzzkIfW/7rc8AAAAgY0hSTQAAbXUA" +
                "AHOgAAD83QAAg2QAAHDoAADsaAAAMD4AABCQ5OyZ6gAAAm5JREFUeNrsmk+L2kAY" +
                "h39JaGkPKi099SC9+AUMSqEXg6jQXr2Knrz5Z6F02+K9dPHgUS/qJ9gvUTyUXfoZ" +
                "LFLYSy8u0UPX+fViitq6JpPEjNAXBgKZhOcJmTeZeUcjiVMOHSceWoj3NgC8AEAA" +
                "3wGsTuWhPAZwAeDnGp7r4wsAj1SHfwrgagN8t30F8ERl+G/3wDvtWkUJt/BKSniF" +
                "V0pCFl4JCb/wkUoEBR++BMmtFgJ8uBKy8IZhsNvtstfrUdf16CRk4UejEZ0YDoc0" +
                "DCMaCRn48XjM3YhSwje8E+Vy+ehjIjD4yWTCeDx+1IEdKHwikThqdlIFXkpCNXhP" +
                "EqrCu5JQHf5eiVOB/6fEQwCTMFJltVplpVIJS+LLmh1nYcC3Wi0KISiEYKPRCEui" +
                "iQOTcCn4drtNIcSf80IINpvNMASuAOD2UMdOpyMNvylRLBaDFrgFgJtDHS3L4nK5" +
                "lIYnydlsxmQyGbTADQBcuulcKpW4WCyk4VOpVBiv0CUAvAJw5+aCbDbLer3OWq3G" +
                "WCwWNfwdgJdOKj2TvVFE8ATwdvdjdn5C8B/2/U54kpjP50rBe5bYHdgqwEtJ2LZN" +
                "kpxOp1vwpmkynU4fHV56TGy2QqFA27Zp2zbz+fzR4X1JOPBO+JSQhpeSyGQyW/Cb" +
                "EqZpBg5/sMhH8jOA925tV6sVNO3v0pumadB1TzXFjwA+BbkyJ52dbNv2+iPn+7XZ" +
                "tzbqWiKXy7Hf73MwGNCyrGjg96xOnyO8qWKw8GGl2KjhNyVEAOACwLuoykxv1isE" +
                "fqaEr1XYavAMwHMAD1z2/wXgx7qC72+vxP/dKhHH7wEASvaA8SqpSrsAAAAASUVO" +
                "RK5CYII=";

        String fileName = photoResource.saveToFile(new Order("COLOR", "PORTRAIT", "0.0", base64String));

        assertThat(fileName).exists().hasHeight(48).hasWidth(48);
    }

    @Test
    public void should_save_to_file_url_string() throws IOException, MachineException {
        String localFileToDownload = this.getClass().getResource("/image.png").toString();

        String fileName = (String) photoResource.saveToFileWithURL(new Order("COLOR", "PORTRAIT", "0.0", localFileToDownload));

        assertThat(fileName).exists().hasHeight(48).hasWidth(48);
    }

    private static ImageAssert assertThat(String actualFileName) {
        return new ImageAssert(actualFileName);
    }

    private static final class ImageAssert extends FileAssert {
        private BufferedImage image;

        protected ImageAssert(String actualFileName) {
            super(new File("src/main/webapp" + File.separator + actualFileName));
        }

        public ImageAssert exists() {
            super.exists();
            return this;
        }

        private ImageAssert hasHeight(int expectedHeight) {
            Assertions.assertThat(getImage().getHeight()).isEqualTo(expectedHeight);
            return this;
        }

        private ImageAssert hasWidth(int expectedWidth) {
            Assertions.assertThat(getImage().getWidth()).isEqualTo(expectedWidth);
            return this;
        }

        private BufferedImage getImage() {
            try {
                if (image == null) {
                    image = ImageIO.read(actual);
                }
                return image;
            } catch (IOException e) {
                fail("Can't create image from File", e);
                throw new RuntimeException(e);
            }
        }
    }

}
