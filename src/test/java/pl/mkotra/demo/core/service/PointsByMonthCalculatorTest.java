package pl.mkotra.demo.core.service;

import org.junit.jupiter.api.Test;
import pl.mkotra.demo.core.model.PointsByMonth;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.model.fixture.TransactionFixture;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PointsByMonthCalculatorTest {

    @Test
    public void shouldCalculateForEmptyTransactions() {
        //when
        List<PointsByMonth> result = PointsByMonthCalculator.calculate(Collections.emptyList());

        //then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void shouldCalculateForBasicCase() {
        //given
        List<Transaction> transactions = List.of(TransactionFixture.transaction());

        //when
        List<PointsByMonth> result = PointsByMonthCalculator.calculate(transactions);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        PointsByMonth actual = result.get(0);
        assertEquals("CUSTOMER_ID", actual.getCustomerId());
        assertEquals(12, actual.getMonth());
        assertEquals(90, actual.getPoints());
    }
}