package au.com.breno.languageDetector.repository;

import au.com.breno.languageDetector.domain.Language;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by brenoreis on 26/07/2016.
 */
public interface LanguageRepository extends Repository<Language, String> {

    Language save(Language language);

    List<Language> findAll();

}
