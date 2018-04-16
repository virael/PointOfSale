package pl.lukan.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class Money {
    private final int MAXIMUM_FRACTION_DIGITS = 2;
    private final int MINIMUM_FRACTION_DIGITS = 2;
    private DecimalFormat decimalFormat;
    private BigDecimal amount;
    private Locale currency;

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
        this.currency = new Locale("pl", "PL");

        bigDecimalConfiguration();
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
        this.currency = new Locale("pl", "PL");

        bigDecimalConfiguration();
    }

    public Money(String amount, Locale currency) {
        this.amount = new BigDecimal(amount);
        this.currency = currency;

        bigDecimalConfiguration();
    }

    public Money(BigDecimal amount, Locale currency) {
        this.amount = amount;
        this.currency = currency;

        bigDecimalConfiguration();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    private void bigDecimalConfiguration() {
        this.amount.setScale(MAXIMUM_FRACTION_DIGITS, RoundingMode.HALF_UP);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(currency);
        decimalFormat = (DecimalFormat) numberFormat;
        initDecimalFormatSettings();
    }

    private void initDecimalFormatSettings() {
        decimalFormat.setMinimumFractionDigits(MINIMUM_FRACTION_DIGITS);
        decimalFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
    }

    public Money add(Money other) {
        if (!isTheSameCurrency(other)) {
            throw new UnsupportedOperationException();
        }

        return new Money(amount.add(other.getAmount()).setScale(MAXIMUM_FRACTION_DIGITS, RoundingMode.HALF_UP), currency);

    }

    private boolean isTheSameCurrency(Money other) {
        return Currency.getInstance(currency).equals(Currency.getInstance(other.currency));
    }

    @Override
    public String toString() {
        return decimalFormat.format(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;

        Money money = (Money) o;

        if (!amount.equals(money.amount)) return false;
        if (!currency.equals(money.currency)) return false;
        if (!decimalFormat.equals(money.decimalFormat)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = decimalFormat.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
}

