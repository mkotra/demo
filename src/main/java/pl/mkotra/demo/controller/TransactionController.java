package pl.mkotra.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mkotra.demo.core.exception.ResourceNotFoundException;
import pl.mkotra.demo.core.factory.OffsetDateTimeFactory;
import pl.mkotra.demo.core.factory.TransactionFactory;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.request.CreateTransactionRequest;
import pl.mkotra.demo.core.service.TransactionService;

import javax.validation.Valid;
import java.util.Collection;

import static pl.mkotra.demo.core.factory.OffsetDateTimeFactory.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/persist-random-data")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void persistRandomData(@RequestParam int size) {
        transactionService.persistRandom(size);
    }

    @GetMapping("/{id}")
    public Transaction find(@PathVariable String id) {
        return transactionService.find(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Transaction create(@RequestBody @Valid CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = TransactionFactory.create(createTransactionRequest, now());
        return transactionService.save(transaction);
    }

    @GetMapping
    public Collection<Transaction> findAll() {
        return transactionService.findAll();
    }
}
