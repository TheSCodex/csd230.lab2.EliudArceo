package csd230.lab2.restControllers.magazineRestUtils;

import csd230.lab2.entities.Magazine;
import csd230.lab2.repositories.MagazineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/magazine")
public class MagazineRestController {

    private final MagazineRepository magazineRepository;

    public MagazineRestController(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @GetMapping()
    List<Magazine> all() {
        return (List<Magazine>) magazineRepository.findAll();
    }

    @GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Magazine getMagazine(@PathVariable int id) {
        Magazine magazine = magazineRepository.findById(id);
        if (magazine == null) {
            throw new MagazineNotFoundException((long) id);
        }
        return magazine;
    }

    @PostMapping()
    Magazine newMagazine(@RequestBody Magazine newMagazine) {
        newMagazine.setDescription("Magazine: " + newMagazine.getTitle());
        return magazineRepository.save(newMagazine);
    }

    @PutMapping("/{id}")
    Magazine replaceMagazine(@RequestBody Magazine newMagazine, @PathVariable Long id) {
        return magazineRepository.findById(id)
                .map(magazine -> {
                    magazine.setTitle(newMagazine.getTitle());
                    magazine.setDescription("Magazine: " + newMagazine.getTitle());
                    magazine.setPrice(newMagazine.getPrice());
                    magazine.setQuantity(newMagazine.getQuantity());
                    return magazineRepository.save(magazine);
                })
                .orElseGet(() -> magazineRepository.save(newMagazine));
    }

    @DeleteMapping("/{id}")
    void deleteMagazine(@PathVariable Long id) {
        magazineRepository.deleteById(id);
    }
}