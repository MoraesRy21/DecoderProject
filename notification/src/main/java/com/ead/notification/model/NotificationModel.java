package com.ead.notification.model;


import com.ead.notification.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "notification", schema = "ead_notification_v2")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notificationId;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 150)
    private String message;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

}