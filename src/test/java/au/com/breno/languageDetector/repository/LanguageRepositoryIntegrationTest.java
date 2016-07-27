package au.com.breno.languageDetector.repository;

import au.com.breno.languageDetector.domain.Language;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.*;

/**
 * Created by brenoreis on 27/07/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class LanguageRepositoryIntegrationTest {

    @Autowired
    private LanguageRepository languageRepository;

    public static final String LANGUAGE = "ENGLISH";

    @Test
    public void should1Save() {

        Language language = languageRepository.save(new Language(LANGUAGE));
        Assert.assertNotNull(language.getId());

    }

    @Test
    public void should2FindAll() {

        List<Language> languages = languageRepository.findAll();

        assertEquals(1, languages.size());

    }


}