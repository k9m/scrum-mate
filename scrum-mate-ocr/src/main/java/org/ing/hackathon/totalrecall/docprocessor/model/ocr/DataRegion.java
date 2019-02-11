package org.ing.hackathon.totalrecall.docprocessor.model.ocr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataRegion {
  private String name;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
}
