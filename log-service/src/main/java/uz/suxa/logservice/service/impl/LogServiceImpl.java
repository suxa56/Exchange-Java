package uz.suxa.logservice.service.impl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import uz.suxa.grpc.LogServiceGrpc;
import uz.suxa.grpc.LogServiceOuterClass;
import uz.suxa.logservice.service.HistoryService;

@GrpcService
public class LogServiceImpl extends LogServiceGrpc.LogServiceImplBase {

    private final HistoryService historyService;

    public LogServiceImpl(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void receive(LogServiceOuterClass.LogRequest request, StreamObserver<LogServiceOuterClass.LogResponse> responseObserver) {
        historyService.getFormattedLog().forEach(x -> {
            LogServiceOuterClass.LogResponse response = LogServiceOuterClass.LogResponse.newBuilder()
                    .setLog(x)
                    .build();

            responseObserver.onNext(response);
        });
        responseObserver.onCompleted();
    }
}
