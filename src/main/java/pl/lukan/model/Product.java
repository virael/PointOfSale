package pl.lukan.model;

public class Product {

    private Long id;
    private String name;
    private Barcode barcode;
    private Money price;

    public Product(Long id, String name, Barcode barcode, Money price) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public Money getPrice() {
        return price;
    }

}