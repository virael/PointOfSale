package pl.lukan.output;

import pl.lukan.model.Barcode;
import pl.lukan.model.Money;
import pl.lukan.model.Product;

import java.util.HashMap;
import java.util.Map;

public final class ProductTableStub {
    private static final Product[] products = {
            new Product(1L, "Chleb", new Barcode("11"), new Money("3.43")),
            new Product(2L, "Mleko", new Barcode("22"), new Money("2.49")),
            new Product(3L, "Czekolada", new Barcode("33"), new Money("1.19")),
            new Product(4L, "Piwo", new Barcode("44"), new Money("4.99"))
    };

    private ProductTableStub() {}

    public static Map<Long, Product> getProductTable() {
        Map<Long, Product> productTable = new HashMap<>();

        for (int i = 0; i < products.length; i++) {
            productTable.put((long) i, products[i]);
        }

        return productTable;
    }
}
