package be.hvwebsites.beveiligd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offertes")
public class OffertesController {
    @GetMapping
    public String offertes(){
        return "offertes";
    }
}
