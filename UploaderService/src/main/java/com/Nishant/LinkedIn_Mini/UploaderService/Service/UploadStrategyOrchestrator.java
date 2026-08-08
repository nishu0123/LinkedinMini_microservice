package com.Nishant.LinkedIn_Mini.UploaderService.Service;

import com.Nishant.LinkedIn_Mini.UploaderService.Constants.UploadProvider;
import com.Nishant.LinkedIn_Mini.UploaderService.Dto.CreatePostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UploadStrategyOrchestrator {

    private final List<UploadStrategy> strategies;

    @Value("${upload.provider:CLOUDINARY}")
    private UploadProvider uploadProvider;

    public UploadStrategyOrchestrator(List<UploadStrategy> strategies) {
        this.strategies = strategies;
        log.info("Injected strategies: {}", strategies);
    }

    public CreatePostResponseDto upload(MultipartFile file) {

        log.info("Strategies inside upload(): {}", strategies);
        log.info("Selected provider: {}", uploadProvider);

        Map<UploadProvider, UploadStrategy> strategyMap =
                strategies.stream()
                        .collect(Collectors.toMap(
                                UploadStrategy::getProvider,
                                Function.identity()
                        ));

        UploadStrategy strategy = strategyMap.get(uploadProvider);

        return strategy.upload(file);
    }
}