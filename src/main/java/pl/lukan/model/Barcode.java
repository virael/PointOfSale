package pl.lukan.model;

import java.util.Objects;

public class Barcode {

    private String barcode;

    public Barcode(String barcode) {

        if(barcode == null) {
            throw new NullPointerException();
        }
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public String toString() {
        return getBarcode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barcode barcode1 = (Barcode) o;
        return Objects.equals(barcode, barcode1.barcode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(barcode);
    }
}