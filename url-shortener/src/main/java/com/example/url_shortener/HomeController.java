package com.example.url_shortener;

import com.example.url_shortener.domain.entities.ShortUrl;
import com.example.url_shortener.domain.repositories.ShortUrlRepository;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Data
public class HomeController {

    private final ShortUrlRepository shortUrlRepository;
    public HomeController(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @GetMapping("/")
    public String Home(Model model) {

        List<ShortUrl> shortUrls = shortUrlRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl" ,"https://localhost:8080/");
        return "index";
    }
}
