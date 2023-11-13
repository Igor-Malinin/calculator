package com.labstests.calculator;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.labstests.calculator.entity.CalculationEntity;
import com.labstests.calculator.repository.CalculationRepository;
import com.labstests.calculator.utility.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(initializers = {IntegrationTests.Initializer.class})
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Интеграционное тестирование")
public class IntegrationTests {

    Calculator calculator = new Calculator();

    @Autowired
    private CalculationRepository repo;

    @Autowired
    private ResourceLoader resourceLoader = null;

    @Autowired
    private MockMvc mockMvc;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("spring_db")
            .withUsername("postgres")
            .withPassword("1");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.enabled=true"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeEach
    public void addCalculations() throws IOException {
        File dataFile = resourceLoader.getResource("classpath:integrdata.json").getFile();
        ObjectMapper mapper = new ObjectMapper();
        CalculationEntity[] calculations = mapper.readValue(dataFile, CalculationEntity[].class);
        repo.saveAll(Arrays.stream(calculations).toList());
    }

    @Test
    @DisplayName("Получение всех записей")
    public void testGetAll() throws Exception {
        this.mockMvc.perform(get("/api/calculations"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Проверка совпадения вычислений")
    public void calculations() throws Exception {
        String result = "";
        List<CalculationEntity> calculationEntities = repo.findAll();
        for (int i = 0; i < calculationEntities.size(); i++) {
            switch (calculationEntities.get(i).getOperation()) {
                case ("*") -> result = calculator.multiply(calculationEntities.get(i).getFirstNum(), calculationEntities.get(i).getSecondNum(), Integer.parseInt(calculationEntities.get(i).getFirstNumSystem()));
                case ("/") -> result = calculator.divide(calculationEntities.get(i).getFirstNum(), calculationEntities.get(i).getSecondNum(), Integer.parseInt(calculationEntities.get(i).getFirstNumSystem()));
                case ("+") -> result = calculator.sum(calculationEntities.get(i).getFirstNum(), calculationEntities.get(i).getSecondNum(), Integer.parseInt(calculationEntities.get(i).getFirstNumSystem()));
                case ("-") -> result = calculator.subtract(calculationEntities.get(i).getFirstNum(), calculationEntities.get(i).getSecondNum(), Integer.parseInt(calculationEntities.get(i).getFirstNumSystem()));
            }
            this.mockMvc.perform(get("/api/calculation/{id}", calculationEntities.get(i).getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(calculationEntities.get(i).getId()))
                    .andExpect(jsonPath("$.result").value(result.toUpperCase()));
        }
    }
    @Test
    @DisplayName("Добавление записи")
    public void testPost() throws Exception {
        CalculationEntity calculationEntity = new CalculationEntity();
        calculationEntity.setFirstNum("45");
        calculationEntity.setFirstNumSystem("10");
        calculationEntity.setSecondNum("5");
        calculationEntity.setSecondNumSystem("10");
        calculationEntity.setOperation("/");
        calculationEntity.setResult("9");
        calculationEntity.setDateTime("18.10.2023, 21:13:19");
        this.mockMvc.perform(post("/api/calculation")
                        .content("{\"firstNum\": \"45\", \"firstNumSystem\": \"10\", \"secondNum\": \"5\", \"secondNumSystem\": \"10\", \"operation\": \"/\", \"result\": \"9\",\"dateTime\": \"18.10.2023, 21:13:19\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}


//    @Test
//    @Transactional
//    @DisplayName("Совпадение количества записей")
//    public void countTest() {
//        long count = repo.count();
//        Assertions.assertEquals(20, count, "Не совпадает количество записей");
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("Совпадение результатов вычислений")
//    public void calculationTest() {
//        String result = "";
//        List<CalculationEntity> calculationEntities = repo.findAll();
//        for (CalculationEntity calculationEntity : calculationEntities) {
//            switch (calculationEntity.getOperation()) {
//                case ("*") -> result = calculator.multiply(calculationEntity.getFirstNum(), calculationEntity.getSecondNum(), Integer.parseInt(calculationEntity.getFirstNumSystem()));
//                case ("/") -> result = calculator.divide(calculationEntity.getFirstNum(), calculationEntity.getSecondNum(), Integer.parseInt(calculationEntity.getFirstNumSystem()));
//                case ("+") -> result = calculator.sum(calculationEntity.getFirstNum(), calculationEntity.getSecondNum(), Integer.parseInt(calculationEntity.getFirstNumSystem()));
//                case ("-") -> result = calculator.subtract(calculationEntity.getFirstNum(), calculationEntity.getSecondNum(), Integer.parseInt(calculationEntity.getFirstNumSystem()));
//            }
//            Assertions.assertEquals(calculationEntity.getResult(), result.toUpperCase(), "Несовпадение результата");
//        }
//    }

//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15");
//
//    @DynamicPropertySource
//    static void postgreSqlProperties (DynamicPropertyRegistry registry) {
//        registry.add("postgresql.driver", postgreSQLContainer::getDriverClassName);
//    }
//
//    @Autowired
//    Environment environment;
//
//                .andExpect(jsonPath("$.id").value("ed76551e-b6ad-4ceb-9af4-9fac22411f0a"))
//                .andExpect(jsonPath("$.firstNum").value("3244"));
//                .andExpect((ResultMatcher) content().string(containsString("")));

//    @Test
//    public void testGet() throws Exception {
//        this.mockMvc.perform(get("/api/calculation/ed76551e-b6ad-4ceb-9af4-9fac22411f0a"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("ed76551e-b6ad-4ceb-9af4-9fac22411f0a"));
//    }
