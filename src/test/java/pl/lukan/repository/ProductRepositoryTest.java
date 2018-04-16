package pl.lukan.repository;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.lukan.model.Barcode;
import pl.lukan.model.Money;
import pl.lukan.model.Product;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class ProductRepositoryTest {
    private ProductRepository productRepository;
    private Product sampleProduct;

    @BeforeClass
    public void setUp() {
        Map<Long, Product> productTable = new HashMap<>();
        sampleProduct = new Product(1L, "Sample name", new Barcode("xx"), new Money("3.5"));
        productTable.put(1L, sampleProduct);
        productRepository = new ProductRepositoryImpl(productTable);
    }

    @Test
    public void shouldFindByBarcode() {
        //when
        Product product = productRepository.findProductByBarcode(new Barcode("xx"));
        //then
        assertThat(product).isEqualTo(sampleProduct);
    }

    @Test(expectedExceptions = ProductNotFoundException.class)
    public void shouldThrowProductNotFoundException() {
        //when
        productRepository.findProductByBarcode(new Barcode("yy"));
    }
}