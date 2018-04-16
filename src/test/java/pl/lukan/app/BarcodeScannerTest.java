package pl.lukan.app;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.lukan.model.Barcode;
import pl.lukan.model.Product;
import pl.lukan.repository.ProductRepository;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BarcodeScannerTest {

    private ProductRepository productRepository;

    @BeforeClass
    public void setUp() {
        productRepository = mock(ProductRepository.class);
    }

    @Test
    public void shouldScan() {
        //given
        BarcodeScanner barcodeScanner = new BarcodeScanner(productRepository);

        Product productMock = mock(Product.class);
        when(productRepository.findProductByBarcode(any(Barcode.class))).thenReturn(productMock);
        //when
        Product product = barcodeScanner.scanProduct(new Barcode("x"));
        //then
        assertThat(product).isEqualTo(productMock);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowInvalidBarcodeException() {
        //given
        BarcodeScanner barcodeScanner = new BarcodeScanner(productRepository);
        //when
        barcodeScanner.scanProduct(new Barcode(""));
    }
}