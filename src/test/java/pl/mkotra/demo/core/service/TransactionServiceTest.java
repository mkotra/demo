package pl.mkotra.demo.core.service;

import org.junit.jupiter.api.Test;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.storage.TransactionDB;
import pl.mkotra.demo.storage.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.parse;

public class TransactionServiceTest {

    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    TransactionService underTest = new TransactionService(transactionRepository);

    @Test
    public void simpleUnitTest() {
        //given
        when(transactionRepository.findAll()).thenReturn(
                List.of(
                        new TransactionDB("ID", "CUSTOMER_ID", new BigDecimal(10), parse("2021-12-12T00:00:00.000Z"))
                )
        );

        //when
        List<Transaction> transactions = underTest.findAll();

        //then
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.get(0);
        assertEquals("ID", transaction.getId());
        assertEquals("CUSTOMER_ID", transaction.getCustomerId());
        assertEquals(new BigDecimal(10), transaction.getAmount());
        assertEquals(parse("2021-12-12T00:00:00.000Z"), transaction.getTimestamp());
    }
}
