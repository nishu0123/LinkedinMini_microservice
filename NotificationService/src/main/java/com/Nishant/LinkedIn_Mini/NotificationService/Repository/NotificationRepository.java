package com.Nishant.LinkedIn_Mini.NotificationService.Repository;
import com.Nishant.LinkedIn_Mini.NotificationService.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.LongFunction;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long > {

    @Query(value = """
        SELECT *
        FROM notifications
        WHERE status = 'FAILED'
          AND retry_count < :maxRetry
        ORDER BY retry_count ASC, created_at ASC
        LIMIT 100
        """, nativeQuery = true)
    List<NotificationEntity> findFailedNotifications(
            @Param("maxRetry") int maxRetry
    );


    NotificationEntity getByNotificationId(UUID notificationId);
}
