package pl.lukan.output;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lukan.controller.ShoppingCart;
import pl.lukan.model.Barcode;
import pl.lukan.model.Money;
import pl.lukan.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LcdDisplayTest {
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutStream;
    String LINE_SEPARATOR = System.lineSeparator();
    String TABBED_SPACE = "\t\t";
    String TOTAL = "TOTAL:";
    int MAXIMUM_FRACTION_DIGITS = 2;
    int MINIMUM_FRACTION_DIGITS = 2;
    Locale DEFAULT_LOCALE = new Locale("pl", "PL");

    @BeforeMethod
    public void setUp() {
        originalSystemOut = System.out;
        systemOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutStream));
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void shouldPrintProductInPLN() {
        //given
        LcdDisplay lcdDisplay = new LcdDisplay();
        BigDecimal price = new BigDecimal("0.33").setScale(2, RoundingMode.HALF_UP);
        Product product = new Product(1L, "Bread", new Barcode("123"), new Money(price, DEFAULT_LOCALE));
        //when
        lcdDisplay.print(product);
        //then
        String expected = "Bread" + TABBED_SPACE + "0,33 zł";
        assertThat(systemOutStream.toString()).isEqualTo(expected);
    }

    @Test
    public void shouldPrintProductNotFound() {
        //given
        LcdDisplay lcdDisplay = new LcdDisplay();
        //when
        lcdDisplay.printProductNotFound();
        //then
        String expected = "Product not found";
        assertThat(systemOutStream.toString()).isEqualTo(expected);
    }

    @Test
    public void shouldPrintInvalidBarcode() {
        //given
        LcdDisplay lcdDisplay = new LcdDisplay();
        //when
        lcdDisplay.printInvalidBarcode();
        //then
        String expected = "Invalid bar-code";
        assertThat(systemOutStream.toString()).isEqualTo(expected);
    }
}