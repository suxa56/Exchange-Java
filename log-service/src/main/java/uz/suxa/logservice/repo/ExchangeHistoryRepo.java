package uz.suxa.logservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.suxa.logservice.model.ExchangeHistory;

public interface ExchangeHistoryRepo extends MongoRepository<ExchangeHistory, String> {
}
