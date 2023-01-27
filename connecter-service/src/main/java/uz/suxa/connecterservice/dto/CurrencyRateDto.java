package uz.suxa.connecterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.suxa.connecterservice.model.Ccy;

@Data
@Builder
@AllArgsConstructor
public class CurrencyRateDto {
    private String ccy;
    private Double officialRate;
    private Double buyingRate;
    private Double sellingRate;
}
