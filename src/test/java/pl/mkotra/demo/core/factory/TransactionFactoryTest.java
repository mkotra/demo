package pl.mkotra.demo.core.factory;

import org.junit.jupiter.api.Test;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.request.CreateTransactionRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

    @Test
    public void shouldCreateTransaction() {
        //given
        CreateTransactionRequest request = new CreateTransactionRequest("CUSTOMER_ID", new BigDecimal("100.00"));
        OffsetDateTime timestamp = OffsetDateTimeFactory.now();

        //when
        Transaction transaction = TransactionFactory.create(request, timestamp);

        //then
        assertNotNull(transaction);
        assertNull(transaction.getId());
        assertEquals(request.customerId(), transaction.getCustomerId());
        assertEquals(request.amount(), transaction.getAmount());
        assertEquals(timestamp, transaction.getTimestamp());
    }
}