package com.example.url_shortener.domain.web.controllers;

import com.example.url_shortener.domain.entities.ShortUrl;
import com.example.url_shortener.domain.service.ShortUrlService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Data
public class HomeController {

    private final ShortUrlService shortUrlService ;


    @GetMapping("/")
    public String Home(Model model) {

        List<ShortUrl> shortUrls = shortUrlService.findPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl" ,"https://localhost:8080/");
        return "index";
    }
}
