package be.hvwebsites.beveiligd.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offertes")
@PreAuthorize("hasAuthority('manager')")
public class OffertesController {
    @GetMapping
    public String offertes(){
        return "offertes";
    }
}
