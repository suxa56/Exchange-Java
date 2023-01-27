package uz.suxa.postdbservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.suxa.postdbservice.model.Ccy;
import uz.suxa.postdbservice.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateRepo extends JpaRepository<CurrencyRate, Long> {

    List<CurrencyRate> findAllByDateEqualsAndCcyEquals(LocalDate date, Ccy ccy);

    @Query(value = "SELECT * FROM currency_rate WHERE actual_date = (SELECT MAX(actual_date) FROM currency_rate)",
            nativeQuery = true)
    List<CurrencyRate> findLastRates();
}
