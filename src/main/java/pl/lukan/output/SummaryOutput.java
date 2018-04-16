package pl.lukan.output;

import pl.lukan.controller.ShoppingCart;

public interface SummaryOutput extends Output {
    void printSummary(ShoppingCart shoppingCart);
}
