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
@RequiredArgsConstructor
public class UploadStrategyOrchestrator {

    List<UploadStrategy> strategies;

    public UploadStrategyOrchestrator(List<UploadStrategy> strategies) {
        this.strategies = strategies;
    }

    //now get the provider
    @Value("${upload.provider:CLOUDINARY}")
    private UploadProvider uploadProvider;

    public CreatePostResponseDto upload(MultipartFile file)
    {
        Map<UploadProvider , UploadStrategy> strategyMap = new HashMap<>();

        strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        UploadStrategy::getProvider,
                        Function.identity()
                ));

        UploadStrategy strategy = strategyMap.get(uploadProvider);


        //calling the strategy

        return strategy.upload(file);

    }


}
