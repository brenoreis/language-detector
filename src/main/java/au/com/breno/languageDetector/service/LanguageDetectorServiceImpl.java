package au.com.breno.languageDetector.service;

import au.com.breno.languageDetector.domain.Language;
import au.com.breno.languageDetector.domain.LanguageProfile;
import au.com.breno.languageDetector.repository.LanguageProfileRepository;
import au.com.breno.languageDetector.repository.LanguageRepository;
import au.com.breno.languageDetector.utils.LanguageDetectorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author brenoreis
 *
 */
@Service
@Transactional
public class LanguageDetectorServiceImpl implements LanguageDetectorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final int TOP_N = 300;
    private static final Integer NOT_FOUND = 1000;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageProfileRepository languageProfileRepository;


    /**
     * Returns a map with the ngram frequencies
     * @param text the text used to generate the ngram frequencies
     * @param n
     * @return
     */
    private Map<String, Integer> getNgrams(String text, int n) {

        Map<String, Integer> ngrams = new HashMap<String, Integer>();

        String content = LanguageDetectorUtils.prepareContent(text);

        //create ngram frequencies
        for (int i=0; i < content.length() - (n-1); i++) {
            String token = content.substring(i, i + n);
            Integer value = ngrams.get(token);
            if (value == null) {
                ngrams.put(token, 1);
            } else {
                ngrams.put(token, ++value);
            }

        }
        return ngrams;
    }


    /**
     * Generates the profile for the input text and returns the topN Ngram entries
     * @param text
     * @return
     */
    @Override
    public Map<String, Integer> generateProfile(String text) {

        Map<String, Integer> biGrams = getNgrams(text, 2);
        Map<String, Integer> triGrams = getNgrams(text, 3);
        Map<String, Integer> ngrams = LanguageDetectorUtils.merge(biGrams, triGrams);
        Map<String, Integer> sortedNgrams = LanguageDetectorUtils.sortDesc(ngrams);
        
        return LanguageDetectorUtils.getTopValues(sortedNgrams, TOP_N);

    }

    /**
     * load language files and creates the profile
     * @param languageMap
     * @return
     */
    @Override
    public String loadLanguageFiles(Map<String, String> languageMap) {

        try {
            for (Map.Entry<String, String> entry : languageMap.entrySet()) {
                log.info("Saving Language: " + entry.getKey());
                Language language = languageRepository.save(new Language(entry.getKey()));
                log.info("Saving Language Profile for : " + entry.getKey());
                Map<String, Integer> profile = generateProfile(entry.getValue());
                saveLanguageProfile(language, profile);
                log.info("Language Profile saved for: " + entry.getKey());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return "An error occurred when loading the files.";
        }

        return "Files have successfully been loaded.";
    }

    /**
     * save language profile
     * @param language
     * @param profile
     */
    private void saveLanguageProfile(Language language, Map<String, Integer> profile) {
        for (Map.Entry<String,Integer> entry : profile.entrySet()) {
            languageProfileRepository.save(new LanguageProfile(language, entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Detects the language for the input string
     * @param input
     * @return
     */
    @Override
    public String detect(String input) {

        Map<String, Integer> inputMap = generateProfile(input);

        List<Language> languages = languageRepository.findAll();

        Map<String, Integer> scores = new HashMap<String, Integer>();

        for (Language language : languages) {

            for (Map.Entry<String, Integer> entry : inputMap.entrySet()) {

                LanguageProfile languageProfile = languageProfileRepository.findByLanguageAndNgram(language, entry.getKey());

                Integer newValue;
                //compute
                if (languageProfile == null) {
                    //not found, penalise
                    newValue = NOT_FOUND;
                } else {
                    //found, compute frequency
                    newValue = Math.abs(languageProfile.getRank() - entry.getValue());
                }

                //add to map
                Integer value = scores.get(language.getName());
                if (value == null) {
                    scores.put(language.getName(), newValue);
                } else {
                    scores.put(language.getName(), value + newValue);
                }
            }
        }

        Map<String, Integer> sortedScores = LanguageDetectorUtils.sortAsc(scores);

        //return top language
        return sortedScores.entrySet().iterator().next().getKey();

    }


}
