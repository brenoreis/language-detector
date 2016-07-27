package au.com.breno.languageDetector.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @author brenoreis
 *
 */
@Service
public interface LanguageDetectorService {

    Map<String, Integer> generateProfile(String text);

    String loadLanguageFiles(Map<String, String> languageMap);

    String detect(String input);
    	
}
