package uz.suxa.logservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeHistoryDto {
    private String operation;
    private String ccy;
    private Double currency;
    private Double uzs;
    private String date;
}
