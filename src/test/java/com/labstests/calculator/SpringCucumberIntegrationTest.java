
package com.labstests.calculator;

import com.labstests.calculator.repository.CalculationRepository;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.labstests.calculator",
        tags = "@all",
        dryRun = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
@ContextConfiguration(classes = CalculatorApplication.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public abstract class SpringCucumberIntegrationTest {

    @Autowired
    @MockBean
    CalculationRepository calculationRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    private ManualRestDocumentation restDocumentation;

    public void setUp() {
        restDocumentation = new ManualRestDocumentation("target/generated-snippets");



        restDocumentation.beforeTest(CucumberTests.class, "setUp");
    }

    public void tearDown() {
        restDocumentation.afterTest();
    }

}