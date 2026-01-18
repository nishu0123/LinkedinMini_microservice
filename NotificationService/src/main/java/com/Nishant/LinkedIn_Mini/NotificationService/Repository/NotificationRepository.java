package com.Nishant.LinkedIn_Mini.NotificationService.Repository;

import com.Nishant.LinkedIn_Mini.NotificationService.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.LongFunction;

@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long > {


}
