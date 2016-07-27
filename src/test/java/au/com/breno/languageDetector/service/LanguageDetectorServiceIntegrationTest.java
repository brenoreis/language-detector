package au.com.breno.languageDetector.service;

import au.com.breno.languageDetector.utils.LanguageDetectorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.springframework.test.annotation.DirtiesContext.ClassMode;


/**
 * Created by brenoreis on 26/07/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class LanguageDetectorServiceIntegrationTest {

    @Autowired
    private LanguageDetectorService languageDetectorService;


    @Test
    public void shouldDetect() throws Exception {

        Map<String, String> map = LanguageDetectorUtils.readFiles(getClass().getClassLoader().getResource("files").getPath());

        String loadResult = languageDetectorService.loadLanguageFiles(map);

        Assert.assertEquals("Files have successfully been loaded.", loadResult);

        String detectResult = languageDetectorService.detect("Can't judge a book by its cover");

        Assert.assertEquals("ENGLISH", detectResult);

        String detectResult2 = languageDetectorService.detect("Nao se pode julgar o livro pela capa");

        Assert.assertEquals("PORTUGUESE", detectResult2);


    }
}