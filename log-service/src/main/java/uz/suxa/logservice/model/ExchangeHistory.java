package uz.suxa.logservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeHistory {
    @Id
    private String id;
    private Operation operation;
    private Ccy ccy;
    private Double currency;
    private Double uzs;
    private LocalDateTime date;
}
