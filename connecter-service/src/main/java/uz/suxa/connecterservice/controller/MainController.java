package uz.suxa.connecterservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.suxa.connecterservice.dto.CurrencyRateDto;
import uz.suxa.connecterservice.service.CurrencyService;
import uz.suxa.connecterservice.service.HistoryService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class MainController {

    private final CurrencyService currencyService;
    private final HistoryService historyService;

    public MainController(CurrencyService currencyService, HistoryService historyService) {
        this.currencyService = currencyService;
        this.historyService = historyService;
    }

    @GetMapping("/api/currencies")
    public ResponseEntity<List<CurrencyRateDto>> getCurrencies() {
        return new ResponseEntity<>(currencyService.getRates(), HttpStatus.OK);
    }

    @GetMapping("/api/logs")
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs;
        try {
             logs= historyService.getLogs().get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PostMapping("/history/save")
    public ResponseEntity<?> saveHistory(@RequestBody String history) {
        historyService.save(history);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
