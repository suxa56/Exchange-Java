package uz.suxa.connecterservice.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import uz.suxa.connecterservice.dto.CurrencyRateDto;
import uz.suxa.grpc.UpdateServiceGrpc;
import uz.suxa.grpc.UpdateServiceOuterClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CurrencyService {

    @GrpcClient("update")
    private UpdateServiceGrpc.UpdateServiceBlockingStub blockingStub;

    private final List<CurrencyRateDto> ratesDto = new ArrayList<>();
    public final Double BANK_COMMISSION = 50.0;

    public List<CurrencyRateDto> getRates() {
        update();
        return this.ratesDto;
    }

    public void update() {
        this.ratesDto.clear();
        UpdateServiceOuterClass.UpdateRequest request = UpdateServiceOuterClass.UpdateRequest.newBuilder()
                .setCommand("update")
                .build();
        Iterator<UpdateServiceOuterClass.UpdateResponse> responses = blockingStub.receive(request);
        while (responses.hasNext()) {
            UpdateServiceOuterClass.UpdateResponse response = responses.next();
            Double rate = response.getRate();
            CurrencyRateDto currencyRateDto = new CurrencyRateDto(
                    response.getCcy(),
                    response.getRate(),
                    rate - BANK_COMMISSION,
                    rate + BANK_COMMISSION
            );
            ratesDto.add(currencyRateDto);
        }
    }
}
