package uz.suxa.postdbservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import uz.suxa.postdbservice.model.Ccy;
import uz.suxa.postdbservice.model.CurrencyRate;
import uz.suxa.postdbservice.repo.CurrencyRateRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CurrencyRateService {

    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final CurrencyRateRepo repo;

    public CurrencyRateService(CurrencyRateRepo repo) {
        this.repo = repo;
    }

    public List<CurrencyRate> getLastRates() {
        return repo.findLastRates();
    }

    public void save(String json) {
        CurrencyRate currencyRate = parseJson(json);
        List<CurrencyRate> equalsData = repo.findAllByDateEqualsAndCcyEquals(currencyRate.getDate(), currencyRate.getCcy());
        if (equalsData.isEmpty()) {
            repo.save(currencyRate);
        }
    }

    private CurrencyRate parseJson(String json) {
        JSONObject data;
        try {
            data = new ObjectMapper().readValue(json, JSONObject.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot parse string " + json + " to jsonObject");
        }
        String rate = data.get("Rate").toString();
        String ccy = data.get("Ccy").toString();
        String date = data.get("Date").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return CurrencyRate.builder()
                .ccy(Ccy.valueOf(ccy))
                .date(LocalDate.parse(date, formatter))
                .rate(Double.parseDouble(rate))
                .build();
    }
}
