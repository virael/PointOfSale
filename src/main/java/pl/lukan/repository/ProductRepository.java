package pl.lukan.repository;

import pl.lukan.model.Barcode;
import pl.lukan.model.Product;

public interface ProductRepository {
    Product findProductByBarcode(Barcode barcode);
}
