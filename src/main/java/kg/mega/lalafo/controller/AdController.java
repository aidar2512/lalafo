package kg.mega.lalafo.controller;

import kg.mega.lalafo.model.Ad;
import kg.mega.lalafo.service.LalafoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdController {

    private final LalafoService service;

    public AdController(LalafoService service) {
        this.service = service;
    }

    @GetMapping("/test-result")
    public String getAds(Model model) {
        List<Ad> ads = service.getAds();
        model.addAttribute("products", ads); // ✅ совпадает с шаблоном
        return "index";
    }

}
