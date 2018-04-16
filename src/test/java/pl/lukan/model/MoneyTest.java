package pl.lukan.model;

import org.testng.annotations.Test;

import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;

public class MoneyTest {

    @Test
    public void shouldAdd() {
        //given
        Money money1 = new Money("3.415");
        Money money2 = new Money("0.13");
        //when
        Money sum = money1.add(money2);
        //then
        assertThat(sum).isEqualTo(new Money("3.55"));
    }

    @Test
    public void shouldAddTheSameCurrency() {
        //given
        Money money1 = new Money("3.415", Locale.US);
        Money money2 = new Money("0.13", new Locale("es", "EC")); //Ecuador uses the USD currency
        //when
        Money sum = money1.add(money2);
        //then
        assertThat(sum).isEqualTo(new Money("3.55", Locale.US));
    }
}