package csd230.lab2;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.repositories.DiscMagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DiscMagRepositoryTests {

    @Autowired
    private DiscMagRepository discMagRepository;

    @Test
    public void testCreateDiscMag() {
        DiscMag discMag = new DiscMag();
        discMag.setHasDisc(true);
        discMag = discMagRepository.save(discMag);
        assertThat(discMag.getId()).isNotNull();
    }

    @Test
    public void testReadDiscMag() {
        DiscMag discMag = new DiscMag();
        discMag.setHasDisc(true);
        discMag = discMagRepository.save(discMag);

        Optional<DiscMag> foundDiscMag = discMagRepository.findById(discMag.getId());
        assertThat(foundDiscMag).isPresent();
        assertThat(foundDiscMag.get().getId()).isEqualTo(discMag.getId());
    }

    @Test
    public void testUpdateDiscMag() {
        DiscMag discMag = new DiscMag();
        discMag.setHasDisc(true);
        discMag = discMagRepository.save(discMag);

        discMag.setHasDisc(false);
        discMag = discMagRepository.save(discMag);
        Optional<DiscMag> updatedDiscMag = discMagRepository.findById(discMag.getId());
        assertThat(updatedDiscMag).isPresent();
        assertThat(updatedDiscMag.get().getHasDisc()).isFalse();
    }

    @Test
    public void testDeleteDiscMag() {
        DiscMag discMag = new DiscMag();
        discMag.setHasDisc(true);
        discMag = discMagRepository.save(discMag);

        Long discMagId = discMag.getId();
        discMagRepository.delete(discMag);
        Optional<DiscMag> deletedDiscMag = discMagRepository.findById(discMagId);
        assertThat(deletedDiscMag).isNotPresent();
    }
}