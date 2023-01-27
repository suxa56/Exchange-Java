package uz.suxa.clientservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.suxa.clientservice.dto.CurrencyRateDto;
import uz.suxa.clientservice.service.DisplayService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
public class MainController {

    private final DisplayService displayService;

    public MainController(DisplayService displayService) {
        this.displayService = displayService;
    }

    @GetMapping
    public String hello(Model model) throws ExecutionException, InterruptedException, TimeoutException {
        final List<CurrencyRateDto> rates = displayService.getFormattedRates().get(5, TimeUnit.SECONDS);
        model.addAttribute("rates", rates);
        model.addAttribute("text", "Works");
        return "index";
    }

    @PostMapping("/history/save")
    public ResponseEntity<?> saveHistory(@RequestBody String history) {
        displayService.saveHistory(history);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
