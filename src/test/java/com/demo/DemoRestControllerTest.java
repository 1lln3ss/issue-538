package com.demo;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.demo.domain.DemoData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoRestControllerTest {

  @LocalServerPort
  private int port;

  private static RequestSpecification spec;
  private static FieldDescriptor[] descriptor;

  @BeforeAll
  static void setup(RestDocumentationContextProvider restDocumentation) throws IOException {
    spec = new RequestSpecBuilder()
        .addFilter(
            documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
        .build();

    descriptor = new FieldDescriptor[] {
            fieldWithPath("prop1").description("Is property 1"),
            fieldWithPath("prop2").description("Is property 2"),
            fieldWithPath("prop3").description("Is property 3"),
            fieldWithPath("prop4").description("Is property 4"),
            fieldWithPath("prop5").description("Is property 5")};
  }

  @Test
  void should_not_be_nullpointer(){
    given(spec)
        .filter(document("demo",
            responseFields().andWithPrefix("[].", descriptor)
        ))
        .port(port)
        .basePath("v1")
    .when()
        .get("/demo")
    .then()
        .contentType(JSON)
        .statusCode(200);
  }

}
