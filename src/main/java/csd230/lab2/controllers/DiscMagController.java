package csd230.lab2.controllers;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.repositories.DiscMagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/disc-mags")
public class DiscMagController {

    DiscMagRepository discMagRepository;
    public DiscMagController(DiscMagRepository discMagRepository) {
        this.discMagRepository = discMagRepository;
    }

    @GetMapping
    public String discMags(Model model) {
        model.addAttribute("discMags", discMagRepository.findAll());
        return "disc-mag/disc-mags";
    }

    @GetMapping("/add-disc-mag")
    public String discMagForm(Model model) {
        model.addAttribute("discMag", new DiscMag());
        return "disc-mag/add-disc-mag";
    }

    @PostMapping("/add-disc-mag")
    public String discMagSubmit(@ModelAttribute DiscMag discMag, Model model) {
        discMag.setDescription("DiscMag: "+discMag.getTitle());
        model.addAttribute("discMag", discMag);
        discMagRepository.save(discMag);
        model.addAttribute("discMags", discMagRepository.findAll());
        return "redirect:/disc-mags";
    }

    @GetMapping("/edit-disc-mag")
    public String edit_discMag(@RequestParam(value = "id", required = false) Integer id, Model model) {
        DiscMag discMag = discMagRepository.findById(id);
        model.addAttribute("discMag", discMag);
        return "disc-mag/edit-disc-mag";
    }

    @PostMapping("/edit-disc-mag")
    public String edit_discMagSubmit(@ModelAttribute DiscMag discMag, Model model) {
        discMag.setDescription("DiscMag: "+discMag.getTitle());
        discMagRepository.save(discMag);
        return "redirect:/disc-mags";
    }

    @PostMapping("/selection")
    public String processSelection(@RequestParam("selectedDiscMags") List<Integer> selectedDiscMagIds) {
        // Process the selected discMag list here...
        System.out.println(selectedDiscMagIds);
        for (Integer id : selectedDiscMagIds) {
            DiscMag discMag = discMagRepository.findById(id);
            discMagRepository.delete(discMag);
        }
        return "redirect:/disc-mags";
    }
}
