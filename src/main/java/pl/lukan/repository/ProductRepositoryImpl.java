package pl.lukan.repository;

import pl.lukan.model.Barcode;
import pl.lukan.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepository {
    private Map<Long, Product> productTable = new HashMap<Long, Product>();

    public ProductRepositoryImpl(Map<Long, Product> productTable) {
        this.productTable = productTable;
    }

    @Override
    public Product findProductByBarcode(Barcode barcode) {
        Product foundProduct = findProductByBarcodeFromProductTable(barcode);

        if (foundProduct == null) {
            throw new ProductNotFoundException("Cannot find product with barcode: " + barcode);
        }

        return foundProduct;
    }

    private Product findProductByBarcodeFromProductTable(Barcode barcode) {
        Product foundProduct = null;
        for(Product product : productTable.values()) {
            if (product.getBarcode().equals(barcode)) {
                foundProduct = product;
                break;
            }
        }
        return foundProduct;
    }}
