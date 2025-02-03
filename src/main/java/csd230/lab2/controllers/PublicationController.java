package csd230.lab2.controllers;

import csd230.lab2.entities.Publication;
import csd230.lab2.repositories.PublicationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    PublicationRepository publicationRepository;
    public PublicationController(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @GetMapping
    public String publications(Model model) {
        model.addAttribute("publications", publicationRepository.findAll());
        return "publication/publications";
    }

    @GetMapping("/add-publication")
    public String publicationForm(Model model) {
        model.addAttribute("publication", new Publication());
        return "publication/add-publication";
    }

    @PostMapping("/add-publication")
    public String publicationSubmit(@ModelAttribute Publication publication, Model model) {
        publication.setDescription("Publication: "+publication.getTitle());
        model.addAttribute("publication", publication);
        publicationRepository.save(publication);
        model.addAttribute("publications", publicationRepository.findAll());
        return "redirect:/publications";
    }

    @PostMapping("/remove-publication")
    public String removePublication(@RequestParam(value = "id", required = false) Integer id, Model model) {
        publicationRepository.deleteById(id.longValue());
        return "redirect:/publications";
    }
}
