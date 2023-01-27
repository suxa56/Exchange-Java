package uz.suxa.postdbservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.suxa.postdbservice.converter.CcyAttributeConverter;
import uz.suxa.postdbservice.converter.LocalDateAttributeConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Convert(converter = CcyAttributeConverter.class)
    private Ccy ccy;
    private Double rate;
    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "actual_date")
    private LocalDate date;
}
