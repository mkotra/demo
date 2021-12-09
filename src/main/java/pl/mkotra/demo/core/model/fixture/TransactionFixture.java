package pl.mkotra.demo.core.model.fixture;

import pl.mkotra.demo.core.factory.OffsetDateTimeFactory;
import pl.mkotra.demo.core.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.now;
import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.parse;

public class TransactionFixture {

    static final List<String> CUSTOMERS = Arrays.asList(
            "CUSTOMER1", "CUSTOMER2", "CUSTOMER3", "CUSTOMER4", "CUSTOMER5"
    );

    static final List<BigDecimal> AMOUNTS = Arrays.asList(
            new BigDecimal("0.50"),
            new BigDecimal("10.00"),
            new BigDecimal("50.30"),
            new BigDecimal("75.20"),
            new BigDecimal("100.25"),
            new BigDecimal("120.50"),
            new BigDecimal("150.75"),
            new BigDecimal("200.70"),
            new BigDecimal("250.50"),
            new BigDecimal("250.50")
    );

    static final List<Integer> MONTHS = Arrays.asList(
            -1, -2, -3
    );

    public static Transaction transaction() {
        return new Transaction(null, "CUSTOMER_ID", new BigDecimal("120.25"), parse("2021-12-12T00:00:00.000Z"));
    }

    public static List<Transaction> random(int size) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Collections.shuffle(CUSTOMERS);
            Collections.shuffle(AMOUNTS);
            Collections.shuffle(MONTHS);
            Transaction transaction = new Transaction(
                    null,
                    CUSTOMERS.get(0),
                    AMOUNTS.get(0),
                    now().plusMonths(MONTHS.get(0))
            );

            transactions.add(transaction);
        }
        return transactions;
    }

    public static List<Transaction> example1() {
        return List.of(
                transaction("CUSTOMER_1", "120.35", "2021-11-12T00:00:00.000Z"),
                transaction("CUSTOMER_1", "120.70", "2021-12-11T00:00:00.000Z"),
                transaction("CUSTOMER_1", "120.70", "2021-12-13T00:00:00.000Z"),
                transaction("CUSTOMER_2", "200.50", "2021-12-12T00:00:00.000Z"),
                transaction("CUSTOMER_2", "70.50", "2021-12-12T00:00:00.000Z")
        );
    }

    private static Transaction transaction(String customerId, String amount, String isoString) {
        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setAmount(new BigDecimal(amount));
        transaction.setTimestamp(OffsetDateTimeFactory.parse(isoString));
        return transaction;
    }
}
