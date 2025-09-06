package com.example.url_shortener.domain.web.controllers;
import com.example.url_shortener.domain.models.ShortUrlDto;
import com.example.url_shortener.domain.service.ShortUrlService;
import com.example.url_shortener.domain.web.dtos.CreateShortUrlForm;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Data
public class HomeController {

    private final ShortUrlService shortUrlService ;


    @GetMapping("/")
    public String Home(Model model) {

        List<ShortUrlDto> shortUrls = shortUrlService.findPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl" ,"https://localhost:8080/");
        model.addAttribute("createShortUrlForm" ,new CreateShortUrlForm(""));
        return "index";
    }
    @PostMapping("/short-urls")
    String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm createShortUrlForm,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        Model model

    )
    {
        if(bindingResult.hasErrors()){
            List<ShortUrlDto> shortUrls = shortUrlService.findPublicShortUrls();
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl" ,"https://localhost:8080/");
            return "index";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Created Short-Url");
        return "redirect:/";

    }
}
