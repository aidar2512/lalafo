package kg.mega.lalafo.controller;

import kg.mega.lalafo.service.LalafoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final LalafoService service;

    public MainController(LalafoService service) {
        this.service = service;
    }

    @GetMapping("/test-result")
    public String index(Model model) {
        model.addAttribute("ads", service.getAds());
        return "index";
    }
}
