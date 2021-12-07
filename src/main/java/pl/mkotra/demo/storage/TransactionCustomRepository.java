package pl.mkotra.demo.storage;

import pl.mkotra.demo.model.PointsByMonth;

import java.util.List;

public interface TransactionCustomRepository {
    List<PointsByMonth> calculatePointsByMonth();
}
