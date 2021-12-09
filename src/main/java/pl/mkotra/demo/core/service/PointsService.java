package pl.mkotra.demo.core.service;

import org.springframework.stereotype.Service;
import pl.mkotra.demo.core.model.PointsByMonth;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.storage.TransactionRepository;

import java.util.List;

import static pl.mkotra.demo.core.service.PointsByMonthCalculator.*;

@Service
public class PointsService {

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public PointsService(TransactionRepository transactionRepository,
                         TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    public List<PointsByMonth> calculateOnDatabase() {
        return transactionRepository.calculatePointsByMonth(FIRST_THRESHOLD, SECOND_THRESHOLD, SECOND_THRESHOLD_FACTOR);
    }

    public List<PointsByMonth> calculatePointsInJava() {
        List<Transaction> transactions = transactionService.findAll();
        return PointsByMonthCalculator.calculate(transactions);
    }

}
