package com.qaguild.trail;

import java.io.IOException;
import java.util.Properties;

public class Config {

  private static Properties properties = init();

  private static Properties init() {
    Properties properties = new Properties();
    try {
      properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return properties;
  }

  public static String getTestRailEndpoint() {
    return properties.getProperty("testrail.endpoint");
  }

  public static String getTestRailUsername() {
    return properties.getProperty("testrail.username");
  }

  public static String getTestRailPassword() {
    return properties.getProperty("testrail.password");
  }

  public static String getTestRailProjectName() {
    return properties.getProperty("testrail.project.name");
  }

  public static String getTestRailSuiteName() {
    return properties.getProperty("testrail.suite.name");
  }
}
