package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Entity.NotificationEntity;
import com.Nishant.LinkedIn_Mini.NotificationService.Repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class NotificationRetryService {

    private final NotificationStrategyOrchestrator notificationStrategyOrchestrator;

    private final ObjectMapper objectMapper;

    private final NotificationRepository notificationRepository;

    //Max-RETRY limit
    private static final int MAX_RETRY = 3;

    public NotificationRetryService(NotificationStrategyOrchestrator notificationStrategyOrchestrator, ObjectMapper objectMapper, NotificationRepository notificationRepository) {
        this.notificationStrategyOrchestrator = notificationStrategyOrchestrator;
        this.objectMapper = objectMapper;
        this.notificationRepository = notificationRepository;
    }


    //here we have create the NotificationRequestDto
    //and then call notify using notificationOrchestrator

    public NotificationRequestDto createNotificationRequestDto(NotificationEntity failedRecord) throws JsonProcessingException {

        NotificationRequestDto request = new NotificationRequestDto();

        request.setNotificationId(failedRecord.getNotificationId());
        request.setEventType(failedRecord.getEventType());
        request.setCreatedAt(failedRecord.getCreatedAt());
        request.setChannel(failedRecord.getDeliveryChannel());
        request.setRecipientEmail(failedRecord.getRecipientEmail());
        request.setRecipientUserId(failedRecord.getRecipientUserId());
        request.setTemplateName(failedRecord.getTemplateName());


        Map<String, Object> payload =
                objectMapper.readValue(
                        failedRecord.getPayload(),
                        new TypeReference<Map<String, Object>>() {}
                );

        request.setPayload(payload);

        return request;
    }

    public List<NotificationEntity> fetchFailedNotificationRecord()
    {
        //here we have to fetch the 100 record

        List<NotificationEntity>  notificationFailedRecord =
                notificationRepository.findFailedNotifications(MAX_RETRY);
        return notificationFailedRecord;

    }



    public void retryFailedNotifications() throws JsonProcessingException {
        //fetch record from the database
        List<NotificationEntity> failedNotificationRecordList = fetchFailedNotificationRecord();

        //now we have list of record
        for(NotificationEntity row : failedNotificationRecordList)
        {
            // This retry attempt is about to happen
            row.setRetryCount(row.getRetryCount() + 1);

            // Persist the attempt count
            notificationRepository.save(row);

            NotificationRequestDto request = createNotificationRequestDto(row);

            notificationStrategyOrchestrator.notify(request);

        }

    }
}
