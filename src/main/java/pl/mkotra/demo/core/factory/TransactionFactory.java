package pl.mkotra.demo.core.factory;

import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.request.CreateTransactionRequest;

import java.time.OffsetDateTime;

public class TransactionFactory {

    public static Transaction create(CreateTransactionRequest request, OffsetDateTime timestamp) {
        Transaction transaction = new Transaction();
        transaction.setCustomerId(request.customerId());
        transaction.setAmount(request.amount());
        transaction.setTimestamp(timestamp);
        return transaction;
    }
}
