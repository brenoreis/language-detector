package au.com.breno.languageDetector.web;

import au.com.breno.languageDetector.service.LanguageDetectorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by brenoreis on 27/07/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LanguageDetectorControllerTest {


    private MockMvc mockMvc;

    @InjectMocks
    private LanguageDetectorController languageDetectorController;

    @Mock
    private LanguageDetectorServiceImpl languageDetectorService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(languageDetectorController).build();
    }

    @Test
    public void shouldReturnIndex() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    public void shouldReturnDetectResult() throws Exception {

        String test = "test";
        String testOk = "test ok";

        when(languageDetectorService.detect(eq(test))).thenReturn(testOk);

        MvcResult result = mockMvc.perform(post("/detect").param("input", test))
                .andExpect(status().isOk())
                .andExpect(view().name("detectResult")).andReturn();

        assertEquals(testOk, result.getModelAndView().getModel().get("language"));

    }
}