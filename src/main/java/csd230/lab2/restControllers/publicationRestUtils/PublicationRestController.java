package csd230.lab2.restControllers.publicationRestUtils;

import csd230.lab2.entities.Publication;
import csd230.lab2.repositories.PublicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/publication")
public class PublicationRestController {

    private final PublicationRepository publicationRepository;

    public PublicationRestController(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @GetMapping()
    List<Publication> all() {
        return (List<Publication>) publicationRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Publication getPublication(@PathVariable int id) {
        Publication publication = publicationRepository.findById(id);
        if (publication == null) {
            throw new PublicationNotFoundException((long) id);
        }
        return publication;
    }

    @PostMapping()
    Publication newPublication(@RequestBody Publication newPublication) {
        newPublication.setDescription("Publication: " + newPublication.getTitle());
        return publicationRepository.save(newPublication);
    }

    @PutMapping("/{id}")
    Publication replacePublication(@RequestBody Publication newPublication, @PathVariable Long id) {
        return publicationRepository.findById(id)
                .map(publication -> {
                    publication.setTitle(newPublication.getTitle());
                    publication.setDescription("Publication: " + newPublication.getTitle());
                    publication.setPrice(newPublication.getPrice());
                    publication.setQuantity(newPublication.getQuantity());
                    return publicationRepository.save(publication);
                })
                .orElseGet(() -> publicationRepository.save(newPublication));
    }

    @DeleteMapping("/{id}")
    void deletePublication(@PathVariable Long id) {
        publicationRepository.deleteById(id);
    }
}
