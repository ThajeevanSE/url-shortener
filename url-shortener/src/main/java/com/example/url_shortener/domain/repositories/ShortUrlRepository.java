package com.example.url_shortener.domain.repositories;

import com.example.url_shortener.domain.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
}
