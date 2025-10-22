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
    public String getProducts(Model model) {
        List<Ad> products = service.getProducts();
        model.addAttribute("products", products);
        return "products";
    }
}
