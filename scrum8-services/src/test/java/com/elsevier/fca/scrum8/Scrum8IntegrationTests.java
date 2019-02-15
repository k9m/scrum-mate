package com.elsevier.fca.scrum8;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class Scrum8IntegrationTests {

  //TODO random port
  public static int mockPort = 9999;
  private static ClientAndServer mockServer;

  @BeforeClass
  public static void setup() {
    System.out.println("Ran the before");
    mockServer = startClientAndServer(mockPort);
  }

  @AfterClass
  public static void teardown() {
    System.out.println("Ran the after");
    mockServer.stop();
  }

}


