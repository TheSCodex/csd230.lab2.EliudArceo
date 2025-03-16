package csd230.lab2.restControllers.discMagRestUtils;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.repositories.DiscMagRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/disc-mag")
public class DiscMagRestController {

    private final DiscMagRepository discMagRepository;

    public DiscMagRestController(DiscMagRepository discMagRepository) {
        this.discMagRepository = discMagRepository;
    }

    @GetMapping()
    List<DiscMag> all() {
        return (List<DiscMag>) discMagRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public DiscMag getDiscMag(@PathVariable int id) {
        DiscMag discMag = discMagRepository.findById(id);
        if (discMag == null) {
            throw new DiscMagNotFoundException((long) id);
        }
        return discMag;
    }

    @PostMapping()
    DiscMag newDiscMag(@RequestBody DiscMag newDiscMag) {
        newDiscMag.setDescription("DiscMag: " + newDiscMag.getTitle());
        return discMagRepository.save(newDiscMag);
    }

    @PutMapping("/{id}")
    DiscMag replaceDiscMag(@RequestBody DiscMag newDiscMag, @PathVariable Long id) {
        return discMagRepository.findById(id)
                .map(discMag -> {
                    discMag.setTitle(newDiscMag.getTitle());
                    discMag.setDescription("DiscMag: " + newDiscMag.getTitle());
                    discMag.setPrice(newDiscMag.getPrice());
                    discMag.setQuantity(newDiscMag.getQuantity());
                    return discMagRepository.save(discMag);
                })
                .orElseGet(() -> discMagRepository.save(newDiscMag));
    }

    @DeleteMapping("/{id}")
    void deleteDiscMag(@PathVariable Long id) {
        discMagRepository.deleteById(id);
    }
}
