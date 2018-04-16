package pl.lukan.service;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lukan.app.BarcodeScanner;
import pl.lukan.model.Product;
import pl.lukan.output.*;
import pl.lukan.repository.ProductRepository;
import pl.lukan.repository.ProductRepositoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.lukan.output.Output.LINE_SEPARATOR;
import static pl.lukan.output.Output.TABBED_SPACE;
import static pl.lukan.output.Output.TOTAL;

public class SaleServiceTest {
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutStream;
    private BarcodeScanner barcodeScanner;

    @BeforeClass
    public void setUpClass() {
        barcodeScanner = setUpBarcodeScanner();
    }

    @BeforeMethod
    public void setUp() {
        setUpSystemOut();

        saleService = new SaleServiceImpl(barcodeScanner, initSingleProductOutput(), initSummaryOutputs());
    }

    private void setUpSystemOut() {
        originalSystemOut = System.out;
        systemOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutStream));
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(originalSystemOut);
    }

    private SaleService saleService;

    private BarcodeScanner setUpBarcodeScanner() {
        Map<Long, Product> productTable = ProductTableStub.getProductTable();
        ProductRepository productRepository = new ProductRepositoryImpl(productTable);
        return new BarcodeScanner(productRepository);
    }

    private List<SingleProductOutput> initSingleProductOutput() {
        List<SingleProductOutput> singleProductOutputs = new ArrayList<>();
        singleProductOutputs.add(new LcdDisplay());
        return singleProductOutputs;
    }

    private List<SummaryOutput> initSummaryOutputs() {
        List<SummaryOutput> summaryOutputs = new ArrayList<>();
        summaryOutputs.add(new LcdDisplay());
        summaryOutputs.add(new Printer());
        return summaryOutputs;
    }

    @Test
    public void shouldScanValidBarcode() {
        //given
        String validBarcode = "11";
        //when
        saleService.scan(validBarcode);
        //then
        String expectedString = "Chleb" + TABBED_SPACE + "3,43 zł";
        assertThat(systemOutStream.toString()).isEqualTo(expectedString);
    }

    @Test
    public void shouldScanEmptyBarcode() {
        //given
        String invalidBarcode = "";
        //when
        saleService.scan(invalidBarcode);
        //then
        String expectedString = "Invalid bar-code";
        assertThat(systemOutStream.toString()).isEqualTo(expectedString);
    }

    @Test
    public void shouldScanBarcodeOfProductNotInDB() {
        //given
        String barcodeNotInDB = "xx";
        //when
        saleService.scan(barcodeNotInDB);
        //then
        String expectedString = "Product not found";
        assertThat(systemOutStream.toString()).isEqualTo(expectedString);
    }

    @Test
    public void shouldScanExitAndPrintSummary() {
        //when
        saleService.scan(SaleService.EXIT);
        //then
        String expectedLcdString = "TOTAL:" + TABBED_SPACE + "0,00 zł";
        String expectedPrinterString = "Name" + TABBED_SPACE + "Price" + LINE_SEPARATOR
                + "TOTAL:" + TABBED_SPACE + "0,00 zł" + LINE_SEPARATOR;
        assertThat(systemOutStream.toString()).isEqualTo(expectedLcdString + expectedPrinterString);
    }
}