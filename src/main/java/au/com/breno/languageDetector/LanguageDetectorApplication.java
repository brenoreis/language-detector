package au.com.breno.languageDetector;

import au.com.breno.languageDetector.service.LanguageDetectorService;
import au.com.breno.languageDetector.utils.LanguageDetectorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class LanguageDetectorApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageDetectorService languageDetectorService;

    @Autowired(required = true)
    private ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(LanguageDetectorApplication.class);

        app.run(args);
    }

    /**
     * Load the human language files to create profiles
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {

        if (strings.length != 0) {
            Map<String, String> languageMap = LanguageDetectorUtils.readFiles(strings[0]);
            String result = languageDetectorService.loadLanguageFiles(languageMap);
            log.info(result);
        } else {
            log.error("Please provide the root path for the human language files.");
        }

    }
}