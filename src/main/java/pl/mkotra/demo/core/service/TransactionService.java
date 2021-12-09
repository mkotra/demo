package pl.mkotra.demo.core.service;

import org.springframework.stereotype.Service;
import pl.mkotra.demo.core.mapper.TransactionMapper;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.model.fixture.TransactionFixture;
import pl.mkotra.demo.storage.TransactionDB;
import pl.mkotra.demo.storage.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void persistRandom(int size) {
        transactionRepository.deleteAll();
        List<Transaction> randomTransactions = TransactionFixture.random(size);
        save(randomTransactions);
    }

    public Optional<Transaction> find(String id) {
        return transactionRepository.findById(id).map(this::toModel);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toModel)
                .toList();
    }

    public List<Transaction> save(List<Transaction> transactions) {
        List<TransactionDB> transactionDBs = transactions.stream().map(this::toDB).toList();
        List<TransactionDB> saved = transactionRepository.saveAll(transactionDBs);
        return saved.stream().map(this::toModel).toList();
    }

    public Transaction save(Transaction transaction) {
        TransactionDB transactionDB = toDB(transaction);
        TransactionDB saved = transactionRepository.save(transactionDB);
        return toModel(saved);
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    private TransactionDB toDB(Transaction transaction) {
        return TransactionMapper.INSTANCE.toDB(transaction);
    }

    private Transaction toModel(TransactionDB transactionDB) {
        return TransactionMapper.INSTANCE.toModel(transactionDB);
    }
}
