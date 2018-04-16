package pl.lukan.output;

import pl.lukan.model.Product;

public interface SingleProductOutput extends Output {
    void print(Product product);
    void printProductNotFound();
    void printInvalidBarcode();
}
