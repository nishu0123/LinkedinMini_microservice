package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationChannel;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class NotificationStrategyOrchestrator {
    List<NotificationStrategy> strategies;
    public NotificationStrategyOrchestrator(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
    }


    public void notify(NotificationRequest request) {

        Map<NotificationChannel, NotificationStrategy> strategyMap = new HashMap<>();;

        strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        NotificationStrategy::getSupportedChannel,
                        Function.identity()
                ));

        NotificationStrategy strategy = strategyMap.get(request.getChannel());

        //channel and the corresponding strategy has been mapped
        strategy.send(request);
    }
}
