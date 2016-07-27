package au.com.breno.languageDetector.repository;

import au.com.breno.languageDetector.domain.Language;
import au.com.breno.languageDetector.domain.LanguageProfile;
import org.springframework.data.repository.Repository;

/**
 * Created by brenoreis on 26/07/2016.
 */
public interface LanguageProfileRepository extends Repository<LanguageProfile, String> {

    LanguageProfile findByLanguageAndNgram(Language language, String ngram);

    LanguageProfile save(LanguageProfile languageProfile);

}
