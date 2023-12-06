package com.labstests.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.NotFoundException;
import com.labstests.calculator.dto.CalculationDto;
import com.labstests.calculator.dto.CreateCalculationDto;
import com.labstests.calculator.dto.converter.CalculationDtoConverter;
import com.labstests.calculator.entity.CalculationEntity;
import com.labstests.calculator.repository.CalculationRepository;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = CalculatorApplication.class)
@CucumberContextConfiguration
public class CucumberTestsDefinition {

    @Autowired
    private CalculationRepository calculationRepository;

    public ObjectMapper mapper = new ObjectMapper();

    @DataTableType
    public CalculationEntity defineCalculationEntity(Map<String, String> entry) {
        CalculationEntity calculationEntity = new CalculationEntity();
        calculationEntity.setFirstNum(entry.get("firstNum"));
        calculationEntity.setFirstNumSystem(entry.get("firstNumSystem"));
        calculationEntity.setSecondNum(entry.get("secondNum"));
        calculationEntity.setSecondNumSystem(entry.get("secondNumSystem"));
        calculationEntity.setOperation(entry.get("operation"));
        calculationEntity.setResult(entry.get("result"));
        calculationEntity.setDateTime(entry.get("dateTime"));

        return calculationEntity;
    }

    @DataTableType
    public CreateCalculationDto defineCreateCalculationDto(Map<String, String> entry) {
        CreateCalculationDto calculationDto = new CreateCalculationDto();
        calculationDto.setFirstNum(entry.get("firstNum"));
        calculationDto.setFirstNumSystem(entry.get("firstNumSystem"));
        calculationDto.setSecondNum(entry.get("secondNum"));
        calculationDto.setSecondNumSystem(entry.get("secondNumSystem"));
        calculationDto.setOperation(entry.get("operation"));
        calculationDto.setResult(entry.get("result"));
        calculationDto.setDateTime(entry.get("dateTime"));

        return calculationDto;
    }

    @ParameterType("\\d{2}\\.\\d{2}\\.\\d{4}, \\d{2}\\:\\d{2}\\:\\d{2}")
    public LocalDate myDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm:ss"));
    }

    @Given("^calculations table$")
    public void calculations_table(List<CalculationEntity> dataTable) throws IOException {
        CalculationEntity[] calculations = mapper.readValue(mapper.writeValueAsString(dataTable), CalculationEntity[].class);
        calculationRepository.saveAll(Arrays.stream(calculations).toList());
    }

    private String status;

    public String getStatus() {
        return status;
    }

    private String getAllResult;

    public String getGetAllResult() {
        return getAllResult;
    }

    @When("^I perform get request \"([^\"]*)\"$")
    public void getAllCalculations(String url) throws Exception {
        status = String.valueOf(RestAssured.given().get(url).getStatusCode());
        getAllResult = RestAssured.given().get(url).getBody().prettyPrint();
    }

    @Then("I should receive response status code 200")
    public void responseAllStatusCode() {
        Assertions.assertEquals("200", getStatus());
    }

    @And("Body should contain calculations that are given into the table")
    public void responseAllBody() throws JsonProcessingException {
        Assertions.assertEquals(
                mapper.writeValueAsString(calculationRepository.findAll()).replaceAll("\\s", ""),
                getGetAllResult().replaceAll("\\s", "")
        );
    }

    String calculationResult;

    @When("^I perform get request \"([^\"]*)\" with parameters (.+) and (.+) and (.+) and (.+)$")
    public void getResult(String url, String firstNum, String secondNum, String operation, String system) {
        calculationResult = String.valueOf(RestAssured.given().get(url + firstNum + "&" + secondNum + "&" + operation + "&" + system).getBody().prettyPrint());
    }

    @Then("^I see the result as (.+)$")
    public void checkResult(String result) {
        System.out.println("Result from get request: " + calculationResult);
        System.out.println("Result from example table: " + result);
        Assertions.assertEquals(result, calculationResult);
    }

    private Response response;

    public Response getResponse() {
        return response;
    }

    @When("^I perform post request \"([^\"]*)\" and I pass below body$")
    public void addNewCalculation(String url, CreateCalculationDto createCalculationDto) throws JsonProcessingException {
        response = RestAssured.given()
            .header("Content-type", "application/json")
            .and().body(mapper.writeValueAsString(createCalculationDto))
            .when().post(url).then().extract().response();
    }

    @Then("I should receive status code 200")
    public void receiveStatusAfterAdding() {
        Assertions.assertEquals(200, getResponse().getStatusCode());
    }


    @And("I should receive body that given into the table")
    public void checkBodyAfterAdding() throws JsonProcessingException {
        CalculationEntity calculationEntity = calculationRepository.findById(getResponse().jsonPath().getString("id")).orElseThrow(() -> new NotFoundException("Calculation not found"));
        Assertions.assertEquals(
                mapper.writeValueAsString(CalculationDtoConverter.convertEntityToDto(calculationEntity)).replaceAll("\\s", ""),
                getResponse().getBody().prettyPrint().replaceAll("\\s", "")
        );
    }

    private Response responseDate;

    public Response getResponseDate() {
        return responseDate;
    }

    @When("^I perform post request \"([^\"]*)\" and I pass this date (.+) and below body$")
    public void postWithDate(String url, String date, CreateCalculationDto createCalculationDto) throws ParseException, JsonProcessingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        createCalculationDto.setDateTime(dateFormat.format(dateFormat.parse(date)));
        responseDate = RestAssured.given()
                .header("Content-type", "application/json")
                .and().body(mapper.writeValueAsString(createCalculationDto))
                .when().post(url).then().extract().response();
    }

    @Then("I receive status code 200")
    public void receiveStatusAfterAddingWithDate() {
        Assertions.assertEquals(200, getResponseDate().getStatusCode());
    }

    @And("^I receive body that given into the table and with given date$")
    public void receiveBodyAndDate() throws JsonProcessingException {
        CalculationEntity calculationEntity = calculationRepository.findById(getResponseDate().jsonPath().getString("id")).orElseThrow(() -> new NotFoundException("Calculation not found"));

        Assertions.assertEquals(
                mapper.writeValueAsString(CalculationDtoConverter.convertEntityToDto(calculationEntity)).replaceAll("\\s", ""),
                getResponseDate().getBody().prettyPrint().replaceAll("\\s", "")
        );

        Assertions.assertEquals("13.11.2023, 13:14:17", getResponseDate().jsonPath().getString("dateTime"));
    }

    @After("@LastTest")
    public void cleanRepo() {
        calculationRepository.deleteAll();
        System.out.println("database cleared");
    }
}


//        for (CreateCalculationDto calculation : calculations) {
//            RestAssured.given()
//                .header("Content-type", "application/json")
//                .and().body(mapper.writeValueAsString(calculation))
//                .when().post("http://localhost:8191/api/calculation");
//        }
//        System.out.println(CalculationDtoConverter.convertEntityToDto(calculations[0]));
//        for (CalculationEntity entity : dataTable) {
//            System.out.println(entity);
//        }



//                .getBody().prettyPrint());
//        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .apply(documentationConfiguration(restDocumentation)
//                        .snippets().withAdditionalDefaults(new WireMockSnippet()))
//                .build();

//        System.out.println(url);
//        resultActions = mockMvc.perform((RequestBuilder) get(url)).andExpect(status().isOk());
