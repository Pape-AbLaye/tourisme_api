package tech.laye.tourisme_api.notification;


import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification , String> {
}
