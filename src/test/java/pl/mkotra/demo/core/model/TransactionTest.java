package pl.mkotra.demo.core.model;

import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.now;
import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.parse;

class TransactionTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2021-01-12T00:00:00.000Z|1",
            "2021-02-12T00:00:00.000Z|2",
            "2021-03-12T00:00:00.000Z|3",
            "2021-04-12T00:00:00.000Z|4",
            "2021-05-12T00:00:00.000Z|5",
            "2021-06-12T00:00:00.000Z|6",
            "2021-07-12T00:00:00.000Z|7",
            "2021-08-12T00:00:00.000Z|8",
            "2021-09-12T00:00:00.000Z|9",
            "2021-10-12T00:00:00.000Z|10",
            "2021-11-12T00:00:00.000Z|11",
            "2021-12-12T00:00:00.000Z|12"
    }, delimiter = '|')
    public void shouldCalculateMonthNumber(String input, int expected) {
        //given
        Transaction transaction = new Transaction("ID", "CUSTOMER_ID", BigDecimal.TEN, parse(input));

        //when
        int month = transaction.month();

        //then
        assertEquals(expected, month);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "-1000.00|100|1|0",
            "0.00|100|1|0",
            "99.00|100|1|0",
            "100.00|100|0|0",
            "110.00|100|0|0",
            "110.00|100|1|10",
            "120.00|100|1|20",
            "1000.00|100|1|900",
            "1000.00|100|100|90000"
    }, delimiter = '|')
    public void shouldCalculatePoints(BigDecimal amount, int threshold, int thresholdFactor, int expected) {
        //given
        Transaction transaction = new Transaction("ID", "CUSTOMER_ID", amount, now());

        //when
        int result = transaction.points(threshold, thresholdFactor, Integer.MAX_VALUE);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldNotFailForLargeAmount() {
        //given
        Transaction transaction = new Transaction("ID", "CUSTOMER_ID", BigDecimal.valueOf(Double.MAX_VALUE), now());

        //when
        int result = transaction.points(100, 1, Integer.MAX_VALUE);

        //then
        assertEquals(9900, result);
    }
}