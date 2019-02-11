package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.ing.hackathon.totalrecall.docprocessor.model.ocr.DataRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OcrService {

  public String process(
          final byte[] imageInBytes,
          final DataRegion region){
    final ITesseract instance = new Tesseract();
    try {
      final BufferedImage img = convertToBufferedImage(imageInBytes);
      final String text = instance.doOCR(
              img,
              generateRectangle(region, img));
      log.info("Text parsed: {}", text);

      return text;
    }
    catch (TesseractException e) {
      log.error("Error while OCR-ing with Tesseract", e);
      throw new RuntimeException(e);
    }
  }

  private Rectangle generateRectangle(
          final DataRegion region,
          final BufferedImage img){


    log.info("Page dimensions: [{},{}]", img.getWidth(), img.getHeight());
    log.info("Coords=>[{},{}] -> [{},{}]",
            region.getX1(),
            region.getY1(),
            region.getX2(),
            region.getY2());

    final int x1 = region.getX1();
    final int y1 = region.getY1();
    final int width = (region.getX2() - region.getX1());
    final int height = (region.getY2() - region.getY1());

    log.info("Rect  =>[{},{}] -> [{},{}]",
            x1,
            y1,
            width,
            height);

    return new Rectangle(x1, y1, width, height);
  }

  private static BufferedImage convertToBufferedImage(final byte[] imageInBytes) {
    try {
      final InputStream in = new ByteArrayInputStream(imageInBytes);
      final BufferedImage bimg = ImageIO.read(in);

      log.info("BufferedImage dimensions: [{},{}]", bimg.getWidth(), bimg.getHeight());

      in.close();

      return bimg;
    }
    catch (IOException e) {
      log.error("Error while creating BufferedImage", e);
      throw new RuntimeException(e);
    }
  }
}
