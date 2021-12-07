package pl.mkotra.demo.core;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import pl.mkotra.demo.model.Transaction;
import pl.mkotra.demo.storage.TransactionDB;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-07T13:39:58+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toModel(TransactionDB transactionDB) {
        if ( transactionDB == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( transactionDB.id() );
        transaction.setCustomerId( transactionDB.customerId() );
        transaction.setAmount( transactionDB.amount() );
        transaction.setTimestamp( transactionDB.timestamp() );

        return transaction;
    }

    @Override
    public TransactionDB toDB(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        String id = null;
        String customerId = null;
        BigDecimal amount = null;
        OffsetDateTime timestamp = null;

        id = transaction.getId();
        customerId = transaction.getCustomerId();
        amount = transaction.getAmount();
        timestamp = transaction.getTimestamp();

        TransactionDB transactionDB = new TransactionDB( id, customerId, amount, timestamp );

        return transactionDB;
    }
}
