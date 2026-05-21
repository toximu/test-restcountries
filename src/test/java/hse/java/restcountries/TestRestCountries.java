package com.example.demo;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestRestCountries {

    @Test
    void getCountryHappyPath() {
        given().baseUri(
                       "https://restcountries.com/v3.1"
               )
               .when().get("/name/russia")
               .then().statusCode(200)
               .body(
                       "capital[0][0]",
                       equalTo("Moscow")
               )
               .body(
                       "continents[0]",
                       hasItem("Asia")
               );
    }

    @Test
    void getCountryWrongName() {
        given().baseUri(
                       "https://restcountries.com/v3.1"
               )
               .when().get("/name/wrong-country")
               .then().statusCode(404);
    }

    @Test
    void getCountriesByCurrency() {
        given().baseUri(
                       "https://restcountries.com/v3.1"
               )
               .when().get("/currency/euro")
               .then().statusCode(200)
               .body(
                       "name.common",
                       hasItems(
                               "France",
                               "Italy",
                               "Spain"
                       )
               )
               .body(
                       "name.common",
                       not(hasItem("USA"))
               );
    }


    @Test
    void canGetGermanyByAlphaCode() {
        given().baseUri(

                       "https://restcountries.com/v3.1"

               )

               .when().get("/alpha/de")

               .then().statusCode(200)
               .body(
                       "name.common",
                       hasItem("Germany")
               );
    }

    @Test
    void listOfCountriesIsNotEmptyAndPopulationGreaterThanZero() {
        given().baseUri(

                       "https://restcountries.com/v3.1"

               )

               .when().get("/all?fields=population")

               .then().statusCode(200)
               .body(not(empty()))
               .body(
                       "population",
                       everyItem(greaterThanOrEqualTo(0))
               );
    }

    @Test
    void findSerbiaBySubregion() {
        given().baseUri(
                       "https://restcountries.com/v3.1"
               )
               .when().get("/subregion/East Europe")
               .then().statusCode(200)
               .body(not(empty()))
               .body(
                       "name.common", hasItem("Serbia")
               );
    }
}

