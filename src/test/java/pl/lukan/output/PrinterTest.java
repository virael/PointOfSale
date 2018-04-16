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
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.lukan.output.Output.LINE_SEPARATOR;
import static pl.lukan.output.Output.TABBED_SPACE;
import static pl.lukan.output.Output.TOTAL;

public class PrinterTest {
    private List<Product> products;
    private ByteArrayOutputStream systemOutStream;
    private PrintStream originalSystemOut;

    @BeforeMethod
    public void setUp() {
        Product[] productsArray = {
                new Product(1L, "Sample product", new Barcode("123"), new Money("3.5")),
                new Product(2L, "Soap", new Barcode("123"), new Money("1.5"))
        };
        products = Arrays.asList(productsArray);

        originalSystemOut = System.out;
        systemOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutStream));
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void shouldPrintSummary() {
        //given
        Printer printer = new Printer();
        ShoppingCart shoppingCartMock = mock(ShoppingCart.class);
        when(shoppingCartMock.getProductsList()).thenReturn(products);
        when(shoppingCartMock.getTotalSum()).thenReturn(new Money("5"));
        //when
        printer.printSummary(shoppingCartMock);
        //then
        String expectedString = getExpectedString();
        assertThat(systemOutStream.toString()).isEqualTo(expectedString);
    }

    private String getExpectedString() {
        return Printer.HEADER
                + "Sample product" + TABBED_SPACE + "3,50 zł" + LINE_SEPARATOR
                + "Soap" + TABBED_SPACE + "1,50 zł" + LINE_SEPARATOR
                + TOTAL + TABBED_SPACE + "5,00 zł" + LINE_SEPARATOR;
    }
}