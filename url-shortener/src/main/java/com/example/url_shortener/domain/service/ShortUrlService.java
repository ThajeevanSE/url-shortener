package com.example.url_shortener.domain.service;

import com.example.url_shortener.domain.entities.ShortUrl;
import com.example.url_shortener.domain.models.ShortUrlDto;
import com.example.url_shortener.domain.repositories.ShortUrlRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ShortUrlService {
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;

    public List<ShortUrlDto> findPublicShortUrls() {

        return shortUrlRepository.findPublicShortUrls().stream().map(entityMapper::toShortUrlDto).toList();
    }


}
