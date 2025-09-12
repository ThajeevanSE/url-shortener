package com.example.url_shortener.domain.web.controllers;
import com.example.url_shortener.ApplicationProperties;
import com.example.url_shortener.domain.models.CreateShortUrlCmd;
import com.example.url_shortener.domain.models.ShortUrlDto;
import com.example.url_shortener.domain.service.ShortUrlService;
import com.example.url_shortener.domain.web.dtos.CreateShortUrlForm;
import com.example.url_shortener.domain.exception.ShortUrlNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Data
public class HomeController {

    private final ShortUrlService shortUrlService ;
    private final ApplicationProperties properties;


    @GetMapping("/")
    public String Home(Model model) {

        List<ShortUrlDto> shortUrls = shortUrlService.findPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl" ,properties.baseUrl());
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
            model.addAttribute("baseUrl" ,properties.baseUrl());
            return "index";
        }
        try{
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(createShortUrlForm.originalUrl());
            var shortUrlDto=shortUrlService.createShortUrl(cmd);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully Created Short-Url"+properties.baseUrl()+"/s/"+shortUrlDto.shortKey());
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "failed to create Short-Url");
        }


        return "redirect:/";

    }
    @GetMapping("/s/{shortKey}")
    String redirectToOriginalUrl(@PathVariable String shortKey) {
        Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey);
        if(shortUrlDtoOptional.isEmpty()) {
            throw new ShortUrlNotFoundException("Invalid short key: "+shortKey);
        }
        ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
        return "redirect:"+shortUrlDto.originalUrl();
    }
    @GetMapping("/login")
    String loginForm() {
        return "login";
    }
}
