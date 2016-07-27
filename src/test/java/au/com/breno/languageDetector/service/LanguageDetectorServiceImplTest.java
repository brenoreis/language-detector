package au.com.breno.languageDetector.service;

import au.com.breno.languageDetector.domain.Language;
import au.com.breno.languageDetector.domain.LanguageProfile;
import au.com.breno.languageDetector.repository.LanguageProfileRepository;
import au.com.breno.languageDetector.repository.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Created by brenoreis on 26/07/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LanguageDetectorServiceImplTest {

    @InjectMocks
    private LanguageDetectorServiceImpl languageDetectorService;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LanguageProfileRepository languageProfileRepository;

    private static final String TEXT = "A hot potato";

    List<String> expectedNgrams;

    @Before
    public void before() {

        /* NGRAMS
        "a ", " h", "ho", "ot", "t ", " p", "po", "ot", "ta", "at", "to",
        "a h", " ho", "hot", "ot ", "t p", " po", "pot", "ota", "tat", "ato"

        "ot" = 2 times (should be on the top of the list
         */

        expectedNgrams = new ArrayList<String>();
        expectedNgrams.add("a ");
        expectedNgrams.add(" h");
        expectedNgrams.add("ho");
        expectedNgrams.add("ot");
        expectedNgrams.add("t ");
        expectedNgrams.add(" p");
        expectedNgrams.add("po");
        expectedNgrams.add("ta");
        expectedNgrams.add("at");
        expectedNgrams.add("to");
        expectedNgrams.add("a h");
        expectedNgrams.add(" ho");
        expectedNgrams.add("hot");
        expectedNgrams.add("ot ");
        expectedNgrams.add("t p");
        expectedNgrams.add(" po");
        expectedNgrams.add("pot");
        expectedNgrams.add("ota");
        expectedNgrams.add("tat");
        expectedNgrams.add("ato");

    }

    @Test
    public void shouldGenerateProfile() throws Exception {



        Map<String, Integer> profile = languageDetectorService.generateProfile(TEXT);

        //size should be 20
        assertEquals(expectedNgrams.size(), profile.entrySet().size());
        //"ot" should be in first position
        assertEquals(profile.entrySet().iterator().next().getKey(), "ot");
        //"ot" should have rank 1
        assertEquals(1, profile.get("ot").intValue());

    }

    @Test
    public void shouldLoadLanguageFiles() throws Exception {

        String english = "ENGLISH";
        String portuguese = "PORTUGUESE";

        Language languageEnglish = new Language(english);
        Language languagePortugues = new Language(portuguese);

        Map<String, String> languageMap = new HashMap<String, String>();

        String englishText = "Universal Declaration of Human Rights";
        /*
        size = 35 {rs=1, de=1,  d=1,  h=1, ht=1, hu=1, sa=1,  o=1,  r=1, ma=1, of=1, um=1, un=1, ec=1, ig=1, on=1, gh=1, io=1, cl=1, al=1, iv=1, er=1, an=1, ve=1, ra=1, ar=1, n =2, at=1, l =1, la=1, ti=1, ri=1, f =1, ni=1, ts=1}

        size = 35 {rsa=1, hum=1, n r=1, dec=1, uma=1, ion=1, igh=1, ecl=1, ara=1, rig=1, an =1, al =1, ati=1,  ri=1, ive=1, cla=1, niv=1, tio=1, man=1, lar=1, sal=1, on =1,  de=1, ver=1, rat=1, hts=1, ers=1, of =1,  hu=1, f h=1, uni=1, l d=1, ght=1,  of=1, n o=1}
        */

        String portugueseText = "Declaracao Universal dos Direitos Humanos";
        /*
        size = 36 {de=1, rs=1, no=1,  d=2, di=1,  h=1, do=1, s =2, hu=1, sa=1, o =1, ma=1,  u=1, um=1, un=1, ec=1, ca=1, ac=1, os=3, ei=1, cl=1, ir=1, al=1, it=1, iv=1, er=1, an=1, ao=1, ra=1, ve=1, ar=1, re=1, l =1, la=1, ni=1, to=1}

        size = 38 {rsa=1, hum=1, dec=1, o u=1, uma=1, dos=1, dir=1, ecl=1, cao=1, ara=1, eit=1, nos=1, al =1, rac=1, rei=1, cla=1, ive=1, tos=1, niv=1, man=1, lar=1, ire=1, sal=1, ver=1, ano=1,  di=1, ito=1, ers=1,  do=1,  hu=1, uni=1, l d=1, ao =1, s d=1,  un=1, s h=1, os =2, aca=1}
         */

        languageMap.put(english, englishText);
        languageMap.put(portuguese, portugueseText);

        when(languageRepository.save(any(Language.class))).thenReturn(languageEnglish).thenReturn(languagePortugues);

        String result = languageDetectorService.loadLanguageFiles(languageMap);

        assertEquals("Files have successfully been loaded.", result);

        //saved two languages
        verify(languageRepository, times(2)).save(any(Language.class));

        //35 + 35 + 36 + 38
        verify(languageProfileRepository, times(35+35+36+38)).save(any(LanguageProfile.class));

    }

    @Test
    public void shouldLoadLanguageFilesThrowException() throws Exception {

        String english = "ENGLISH";
        String portuguese = "PORTUGUESE";

        Map<String, String> languageMap = new HashMap<String, String>();

        String englishText = "Universal Declaration of Human Rights";
        String portugueseText = "Declaracao Universal dos Direitos Humanos";

        languageMap.put(english, englishText);
        languageMap.put(portuguese, portugueseText);

        when(languageRepository.save(any(Language.class))).thenThrow(Exception.class);

        String result = languageDetectorService.loadLanguageFiles(languageMap);

        assertEquals("An error occurred when loading the files.", result);

        //tried to save one language
        verify(languageRepository, times(1)).save(any(Language.class));

        //saved no language profiles
        verify(languageProfileRepository, times(0)).save(any(LanguageProfile.class));

    }

    @Test
    public void shouldDetect() throws Exception {



    }
}