package pl.lukan.controller;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.lukan.model.Money;
import pl.lukan.model.Product;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartTest {

    private Product productMock1;
    private Product productMock2;
    private Product productMock3;

    @BeforeClass
    public void setUp() {
        productMock1 = mock(Product.class);
        when(productMock1.getPrice()).thenReturn(new Money("3.465"));
        productMock2 = mock(Product.class);
        when(productMock2.getPrice()).thenReturn(new Money("1.46"));
        productMock3 = mock(Product.class);
        when(productMock3.getPrice()).thenReturn(new Money("1.65"));
    }

    @Test
    public void shouldAddOneProduct() {
        //given
        ShoppingCart cart = new ShoppingCart();
        //when
        cart.add(productMock1);
        //then
        assertThat(cart.getProductsList().get(0)).isEqualTo(productMock1);
        assertThat(cart.getTotalSum()).isEqualTo(new Money("3.47"));
    }

}