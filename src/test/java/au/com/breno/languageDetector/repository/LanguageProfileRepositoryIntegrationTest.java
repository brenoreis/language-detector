package au.com.breno.languageDetector.repository;

import au.com.breno.languageDetector.domain.Language;
import au.com.breno.languageDetector.domain.LanguageProfile;
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
public class LanguageProfileRepositoryIntegrationTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageProfileRepository languageProfileRepository;

    public static final String NGRAM = "ha";
    public static final String LANGUAGE = "ENGLISH";
    public static final Integer RANK = 1;



    @Test
    public void should1Save() {

        Language language = languageRepository.save(new Language(LANGUAGE));

        LanguageProfile languageProfile = languageProfileRepository.save(new LanguageProfile(language, NGRAM, RANK));

        Assert.assertNotNull(languageProfile.getId());

    }

    @Test
    public void should2FindByLanguageAndNgram() {

        List<Language> languages = languageRepository.findAll();

        LanguageProfile languageProfile = languageProfileRepository.findByLanguageAndNgram(languages.get(0), NGRAM);

        assertEquals(RANK, languageProfile.getRank());

    }


}