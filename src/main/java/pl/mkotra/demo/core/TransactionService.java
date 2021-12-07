package pl.mkotra.demo.core;

import org.springframework.stereotype.Service;
import pl.mkotra.demo.model.Transaction;
import pl.mkotra.demo.model.TransactionFixture;
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
        return transactionRepository.findById(id).map(TransactionMapper.INSTANCE::toModel);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper.INSTANCE::toModel)
                .toList();
    }

    public List<Transaction> save(List<Transaction> transactions) {
        List<TransactionDB> transactionDBs = transactions.stream().map(TransactionMapper.INSTANCE::toDB).toList();
        List<TransactionDB> saved = transactionRepository.saveAll(transactionDBs);
        return saved.stream().map(TransactionMapper.INSTANCE::toModel).toList();
    }

    public Transaction save(Transaction transaction) {
        TransactionDB transactionDB = TransactionMapper.INSTANCE.toDB(transaction);
        TransactionDB saved = transactionRepository.save(transactionDB);
        return TransactionMapper.INSTANCE.toModel(saved);
    }
}
