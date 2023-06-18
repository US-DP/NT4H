package org.springframework.samples.nt4h.card.hero;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class HeroTest {

    @Autowired
    private Validator validator;
    @Autowired
    private HeroRepository heroRepository;

    private Hero hero;

    @BeforeEach
    public void setup() {
        hero = new Hero();
        hero.setName("Test Hero.kt");
        hero.setHealth(10);
        hero.setRole(Role.EXPLORER);
        hero.setAbilities(Lists.newArrayList());
        hero.setCapacities(Lists.newArrayList());
        hero.setMaxUses(1);
    }

    @Test
    public void testHeroProperties() {
        assertThat(hero.getName()).isEqualTo("Test Hero.kt");
        assertThat(hero.getHealth()).isEqualTo(10);
        assertThat(hero.getRole()).isEqualTo(Role.EXPLORER);
        assertThat(hero.getAbilities()).isEmpty();
        assertThat(hero.getCapacities()).isEmpty();
    }

    @Test
    public void testHeroConstraints() {
        hero.setName("");
        assertThat(validator.validate(hero)).isNotEmpty();
        hero.setName("A");
        assertThat(validator.validate(hero)).isNotEmpty();
        hero.setName("This is a valid name");
        assertThat(validator.validate(hero)).isEmpty();

        hero.setHealth(null);
        assertThat(validator.validate(hero)).isEmpty();
        hero.setHealth(0);
        assertThat(validator.validate(hero)).isEmpty();

        hero.setRole(null);
        assertThat(validator.validate(hero)).isEmpty();
        hero.setRole(Role.EXPLORER);
        assertThat(validator.validate(hero)).isEmpty();
    }

    @Test
    public void testHeroLifecycle() {
        hero = heroRepository.save(hero);
        assertThat(hero.getId()).isNotNull();

        hero.setName("Updated Hero.kt");
        hero = heroRepository.save(hero);
        assertThat(hero.getName()).isEqualTo("Updated Hero.kt");

        heroRepository.delete(hero);
        assertThat(heroRepository.findById(hero.getId())).isEmpty();
    }

    @Test
    public void testHeroQueries() {
        heroRepository.save(hero);
        assertThat(heroRepository.findAll()).isNotEmpty();

        assertThat(heroRepository.findByName("Test Hero.kt")).isNotNull();
    }
}
