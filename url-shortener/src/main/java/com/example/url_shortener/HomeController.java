package com.example.url_shortener;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home(Model model) {
        model.addAttribute("title", "Thajee");
        return "index";
    }

    @GetMapping("/about")
    public String About() {
        return "about";
    }
    @GetMapping("/layout")
    public String Layout() {
        return "layout";
    }
}
