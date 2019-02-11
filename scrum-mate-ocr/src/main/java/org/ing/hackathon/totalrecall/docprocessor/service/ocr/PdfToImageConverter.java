package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfToImageConverter {

  public byte[] convert(final byte[] pdfBytes, final int page){
    try {
      return convert(PDDocument.load(pdfBytes), page);
    } catch (IOException e) {
      log.error("Error when conerting: {}", e);
      throw new RuntimeException(e);
    }
  }

  public byte[] convert(final PDDocument document, final int page) throws IOException {
    final PDFRenderer pdfRenderer = new PDFRenderer(document);
    final BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bim, "jpg", baos);
    baos.flush();
    final byte[] imageBytes = baos.toByteArray();
    baos.close();

    return imageBytes;
  }


}

