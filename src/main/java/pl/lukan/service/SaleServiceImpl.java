package pl.lukan.service;

import pl.lukan.app.BarcodeScanner;
import pl.lukan.app.InvalidBarcodeException;
import pl.lukan.repository.ProductNotFoundException;
import pl.lukan.controller.ShoppingCart;
import pl.lukan.model.Barcode;
import pl.lukan.model.Product;
import pl.lukan.output.SingleProductOutput;
import pl.lukan.output.SummaryOutput;

import java.util.List;
import java.util.Locale;

public class SaleServiceImpl implements SaleService {

    public static final String EXIT = "exit";
    private BarcodeScanner barcodeScanner;
    private List<SingleProductOutput> singleProductOutputs;
    private List<SummaryOutput> summaryOutputs;
    private ShoppingCart shoppingCart;
    static Locale DEFAULT_LOCALE = new Locale("pl", "PL"); // 1 static?

    public SaleServiceImpl(BarcodeScanner barcodeScanner, List<SingleProductOutput> singleProductOutputs,
                           List<SummaryOutput> summaryOutputs) {
        this(barcodeScanner, singleProductOutputs, summaryOutputs, new ShoppingCart(DEFAULT_LOCALE)); // 1
    }

    public SaleServiceImpl(BarcodeScanner barcodeScanner, List<SingleProductOutput> singleProductOutputs,
                           List<SummaryOutput> summaryOutputs, ShoppingCart cart) {
        this.barcodeScanner = barcodeScanner;
        this.singleProductOutputs = singleProductOutputs;
        this.summaryOutputs = summaryOutputs;
        this.shoppingCart = cart;
    }

    public static void checkNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }

    public void scan(String input) {

        checkNotNull(input);

        if (isExit(input)) {
            printSummary();
            return;
        }

        scanSingleProductAndHandleExceptions(input);
    }

    private boolean isExit(String input) {
        return input.equals(EXIT);
    }

    private void scanSingleProductAndHandleExceptions(String input) {
        try {
            scanSingleProduct(new Barcode(input));
        } catch (ProductNotFoundException e) {
            printProductNotFound();
        } catch (InvalidBarcodeException e) {
            printInvalidBarcode();
        }
    }

    private void scanSingleProduct(Barcode barcode) {
        Product product = barcodeScanner.scanProduct(barcode);
        shoppingCart.add(product);
        print(product);
    }

    private void printSummary() {
        for (SummaryOutput summaryOutput : summaryOutputs) {
            summaryOutput.printSummary(shoppingCart);
        }
    }

    private void printInvalidBarcode() {
        for (SingleProductOutput singleProductOutput : singleProductOutputs) {
            singleProductOutput.printInvalidBarcode();
        }
    }

    private void printProductNotFound() {
        for (SingleProductOutput singleProductOutput : singleProductOutputs) {
            singleProductOutput.printProductNotFound();
        }
    }

    private void print(Product product) {
        for (SingleProductOutput singleProductOutput : singleProductOutputs) {
            singleProductOutput.print(product);
        }
    }

}
