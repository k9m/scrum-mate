package com.elsevier.fca.scrum8;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;

public class JsonUtils {

  public static String readJSON(final String filePath) {
    try {
      return StreamUtils.copyToString(new ClassPathResource(filePath).getInputStream(), Charset.forName("UTF-8"));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}