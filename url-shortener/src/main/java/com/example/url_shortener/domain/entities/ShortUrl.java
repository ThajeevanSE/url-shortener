package com.example.url_shortener.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="short_urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true ,nullable = false)
    private String shortKey;

    @Column(nullable = false)
    private String originalUrl;

    @ManyToOne
    @JoinColumn(name="created_by")
    private User createdBy;

    private Boolean isPrivate;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime expiresAt;
    private Long clickCount;

}
