package com.example.url_shortener.domain.web.controllers;
import com.example.url_shortener.ApplicationProperties;
import com.example.url_shortener.domain.entities.User;
import com.example.url_shortener.domain.models.CreateShortUrlCmd;
import com.example.url_shortener.domain.models.PagedResult;
import com.example.url_shortener.domain.models.ShortUrlDto;
import com.example.url_shortener.domain.service.ShortUrlService;
import com.example.url_shortener.domain.web.dtos.CreateShortUrlForm;
import com.example.url_shortener.domain.exception.ShortUrlNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Data
public class HomeController {

    private final ShortUrlService shortUrlService ;
    private final ApplicationProperties properties;
    private final SecurityUtils securityUtils;


    @GetMapping("/")
    public String Home(
            @RequestParam(defaultValue = "1") Integer page,
            Model model) {
        this.addShortUrlsDataToModel(model, page);
        model.addAttribute("paginationUrl", "/");
        model.addAttribute("createShortUrlForm",
                new CreateShortUrlForm("", false, null));
        return "index";
    }
    private void addShortUrlsDataToModel(Model model, int pageNo) {
        PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls(pageNo, properties.pageSize());
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
    }
    @PostMapping("/short-urls")
    String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm createShortUrlForm,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        Model model

    )
    {
        if(bindingResult.hasErrors()) {
            this.addShortUrlsDataToModel(model, 1);
            return "index";
        }
        try{
            Long userId = securityUtils.getCurrentUserId();
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(
                    createShortUrlForm.originalUrl(),
                    createShortUrlForm.isPrivate(),
                    createShortUrlForm.expirationInDays(),
                    userId
            );
            var shortUrlDto=shortUrlService.createShortUrl(cmd);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully Created Short-Url"+properties.baseUrl()+"/s/"+shortUrlDto.shortKey());
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "failed to create Short-Url");
        }


        return "redirect:/";

    }
    @GetMapping("/s/{shortKey}")
    String redirectToOriginalUrl(@PathVariable String shortKey) {
        Long userId = securityUtils.getCurrentUserId();
        Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey,userId);
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


    @PostMapping("/delete-urls")
    public String deleteUrls(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "No URLs selected for deletion");
            return "redirect:/my-urls";
        }
        try {
            var currentUserId = securityUtils.getCurrentUserId();
            shortUrlService.deleteUserShortUrls(ids, currentUserId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Selected URLs have been deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error deleting URLs: " + e.getMessage());
        }
        return "redirect:/my-urls";
    }
    @GetMapping("/my-urls")
    public String showUserUrls(
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        var currentUserId = securityUtils.getCurrentUserId();
        PagedResult<ShortUrlDto> myUrls =
                shortUrlService.getUserShortUrls(currentUserId, page, properties.pageSize());
        model.addAttribute("shortUrls", myUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("paginationUrl", "/my-urls");
        return "my-urls";
    }

}
