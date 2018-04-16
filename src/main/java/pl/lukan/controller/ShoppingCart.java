package pl.lukan.controller;

import pl.lukan.model.Money;
import pl.lukan.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {
    private List<Product> productsList;
    private Locale currency;
    static Locale DEFAULT_LOCALE = new Locale("pl", "PL");

    public ShoppingCart(Locale currency) {
        this.productsList = new ArrayList<>();
        this.currency = currency;
    }

    public ShoppingCart() {
        this(DEFAULT_LOCALE);
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public Money getTotalSum() {
        Money totalSum = new Money(BigDecimal.ZERO, currency);
        for (Product product : productsList) {
            totalSum = totalSum.add(product.getPrice());
        }
        return totalSum;
    }

    public void add(Product product) {
        if(product == null) {
            throw new IllegalArgumentException();
        }

        productsList.add(product);
    }
}
