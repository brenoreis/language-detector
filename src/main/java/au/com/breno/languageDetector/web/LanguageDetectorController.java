package au.com.breno.languageDetector.web;

import au.com.breno.languageDetector.service.LanguageDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 
 * @author brenoreis
 *
 */
@Controller
public class LanguageDetectorController {

	@Autowired
	private LanguageDetectorService languageDetectorService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public String detect(Map<String, Object> model, @RequestParam("input") String input) {
        model.put("input", input);
        model.put("language", languageDetectorService.detect(input));
        return "detectResult";
    }
	
}
