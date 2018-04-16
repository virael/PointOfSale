package pl.lukan.app;

import pl.lukan.model.Barcode;
import pl.lukan.model.Product;
import pl.lukan.repository.ProductRepository;

public class BarcodeScanner {
    ProductRepository productRepository;

    public BarcodeScanner(ProductRepository productRepository) {
        if(productRepository == null) {
            throw new IllegalArgumentException();
        }
        this.productRepository = productRepository;
    }

    public Product scanProduct(Barcode barcode) {
        String code = barcode.getBarcode();

        if (code.isEmpty() && code != null) {
            throw new InvalidBarcodeException("Invalid barcode: " + code);
        }

        return productRepository.findProductByBarcode(barcode);
    }

}
