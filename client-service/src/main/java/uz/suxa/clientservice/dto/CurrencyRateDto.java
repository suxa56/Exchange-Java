package uz.suxa.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDto {
    private String ccy;
    private Double officialRate;
    private Double buyingRate;
    private Double sellingRate;
}
