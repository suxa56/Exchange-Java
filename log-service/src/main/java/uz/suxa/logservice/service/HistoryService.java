package uz.suxa.logservice.service;

import org.springframework.stereotype.Service;
import uz.suxa.logservice.dto.ExchangeHistoryDto;
import uz.suxa.logservice.model.Ccy;
import uz.suxa.logservice.model.ExchangeHistory;
import uz.suxa.logservice.model.Operation;
import uz.suxa.logservice.repo.ExchangeHistoryRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final ExchangeHistoryRepo repo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HistoryService(ExchangeHistoryRepo repo) {
        this.repo = repo;
    }

    public void insert(ExchangeHistoryDto dto) {
        ExchangeHistory history = ExchangeHistory.builder()
                .currency(dto.getCurrency())
                .ccy(Ccy.valueOf(dto.getCcy()))
                .date(LocalDateTime.parse(dto.getDate(), formatter))
                .uzs(dto.getUzs())
                .operation(Operation.valueOf(dto.getOperation().toUpperCase()))
                .build();
        repo.insert(history);
    }

    public List<String> getFormattedLog() {
        return repo.findAll().stream().map(ExchangeHistory::toString).collect(Collectors.toList());
    }
}
