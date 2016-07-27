package au.com.breno.languageDetector.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by brenoreis on 27/07/2016.
 */
public class LanguageDetectorUtilsTest {

    @Test
    public void shouldMerge() throws Exception {
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("1", 1);
        map1.put("2", 2);
        Map<String, Integer> map2 = new HashMap<String, Integer>();
        map1.put("3", 3);
        map1.put("4", 4);

        Map<String, Integer> map3 = LanguageDetectorUtils.merge(map1, map2);

        Assert.assertEquals(4, map3.entrySet().size());

    }

    @Test
    public void shouldSortAsc() throws Exception {
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("3", 3);
        map1.put("2", 2);
        map1.put("1", 1);

        Map<String, Integer> map2 = LanguageDetectorUtils.sortAsc(map1);

        assertEquals(1, map2.entrySet().iterator().next().getValue().intValue());

    }

    @Test
    public void shouldSortDesc() throws Exception {
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("1", 1);
        map1.put("2", 2);
        map1.put("3", 3);

        Map<String, Integer> map2 = LanguageDetectorUtils.sortDesc(map1);

        assertEquals(3, map2.entrySet().iterator().next().getValue().intValue());

    }

    @Test
    public void shouldGetTopValues() throws Exception {

        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("1", 1);
        map1.put("2", 2);
        map1.put("3", 3);

        Map<String, Integer> map2 = LanguageDetectorUtils.getTopValues(map1, 2);

        assertEquals(2, map2.entrySet().size());

    }

    @Test
    public void shouldPrepareContent() throws Exception {

        String text = "Genius: is, one percent inspiration! and 99 percent? perspiration.";

        String result = LanguageDetectorUtils.prepareContent(text);

        String expected = "genius is one percent inspiration and percent perspiration";

        assertEquals(expected, result);

    }

    @Test
    public void shouldReadFile() throws Exception {
        String result = LanguageDetectorUtils.readFile(getClass().getClassLoader().getResource("files/ENGLISH.2").getPath(), Charset.defaultCharset());

        String expected = "Universal Declaration of Human Rights";

        assertEquals(expected, result);

    }

    @Test
    public void shouldReadFiles() throws Exception {
        Map<String, String> map = LanguageDetectorUtils.readFiles(getClass().getClassLoader().getResource("files").getPath());

        assertEquals(2, map.size());
    }
}