package pl.lukan.output;

import pl.lukan.controller.ShoppingCart;
import pl.lukan.model.Money;
import pl.lukan.model.Product;

import java.util.List;

public class Printer implements SummaryOutput {

    public static final String HEADER = "Name" + TABBED_SPACE + "Price" + LINE_SEPARATOR;

    @Override
    public void printSummary(ShoppingCart shoppingCart) {
        StringBuilder sb = new StringBuilder(100);

        buildContent(shoppingCart.getProductsList(), shoppingCart.getTotalSum(), sb);

        System.out.print(sb);
    }

    private void buildContent(List<Product> products, Money totalSum, StringBuilder sb) {
        buildHeader(sb);
        buildBody(sb, products);
        buildFooter(sb, totalSum);
    }

    private void buildHeader(StringBuilder sb) {
        sb.append(HEADER);
    }

    private void buildBody(StringBuilder sb, List<Product> products) {
        for(Product product : products) {
            sb.append(product.getName())
                    .append(TABBED_SPACE)
                    .append(product.getPrice())
                    .append(LINE_SEPARATOR);
        }
    }

    private void buildFooter(StringBuilder sb, Money totalSum) {
        sb.append(TOTAL)
                .append(TABBED_SPACE)
                .append(totalSum)
                .append(LINE_SEPARATOR);
    }
}
