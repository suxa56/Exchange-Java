package uz.suxa.postdbservice.service.impl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.scheduling.annotation.Async;
import uz.suxa.grpc.UpdateServiceGrpc;
import uz.suxa.grpc.UpdateServiceOuterClass;
import uz.suxa.postdbservice.service.CurrencyRateService;

@GrpcService
public class UpdateServiceImpl extends UpdateServiceGrpc.UpdateServiceImplBase {

    private final CurrencyRateService currencyRateService;

    public UpdateServiceImpl(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @Override
    @Async
    public void receive(UpdateServiceOuterClass.UpdateRequest request,
                        StreamObserver<UpdateServiceOuterClass.UpdateResponse> responseObserver) {
        currencyRateService.getLastRates().forEach(currencyRate -> {
            UpdateServiceOuterClass.UpdateResponse response = UpdateServiceOuterClass
                    .UpdateResponse.newBuilder()
                    .setCcy(currencyRate.getCcy().name())
                    .setRate(currencyRate.getRate())
                    .setDate(currencyRate.getDate().format(currencyRateService.formatter))
                    .build();

            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }
}
