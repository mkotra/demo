package pl.mkotra.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.mkotra.demo.core.TransactionService;
import pl.mkotra.demo.exception.NotFoundException;
import pl.mkotra.demo.factory.TransactionFactory;
import pl.mkotra.demo.model.Transaction;
import pl.mkotra.demo.request.CreateTransactionRequest;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/persist-random")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void persistRandomData(@RequestParam int size) {
        transactionService.persistRandom(size);
    }

    @GetMapping("/{id}")
    public Transaction find(@PathVariable String id) {
        return transactionService.find(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Transaction create(@RequestBody @Valid CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = TransactionFactory.create(createTransactionRequest, OffsetDateTime.now());
        return transactionService.save(transaction);
    }

    @GetMapping
    public Collection<Transaction> findAll() {
        return transactionService.findAll();
    }
}
