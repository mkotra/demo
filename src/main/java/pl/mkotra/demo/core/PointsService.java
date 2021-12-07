package pl.mkotra.demo.core;

import org.springframework.stereotype.Service;
import pl.mkotra.demo.model.PointsByMonth;
import pl.mkotra.demo.storage.TransactionRepository;

import java.util.List;

@Service
public class PointsService {

    private final TransactionRepository transactionRepository;

    public PointsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<PointsByMonth> pointsByMonth() {
        return transactionRepository.calculatePointsByMonth();
    }
}
