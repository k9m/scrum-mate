package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import org.ing.hackathon.totalrecall.docprocessor.model.ocr.DataRegion;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class OcrServiceTest {

    private static final List<DataRegion> regions = Arrays.asList(
            DataRegion.builder()
                    .name("topLeft")
                    .x1(0)
                    .y1(858)
                    .x2(400)
                    .y2(1125)
                    .build(),
            DataRegion.builder()
                    .name("toDo")
                    .x1(490)
                    .y1(1047)
                    .x2(963)
                    .y2(2707)
                    .build(),
            DataRegion.builder()
                    .name("inProgress")
                    .x1(1048)
                    .y1(1047)
                    .x2(1490)
                    .y2(2707)
                    .build(),
            DataRegion.builder()
                    .name("done")
                    .x1(1555)
                    .y1(1047)
                    .x2(2025)
                    .y2(2707)
                    .build(),
            DataRegion.builder()
                    .name("bottomRight")
                    .x1(2071)
                    .y1(2361)
                    .x2(2482)
                    .y2(2707)
                    .build()
    );

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        URL url = this.getClass().getResource("/test.pdf");
        File testWsdl = new File(url.getFile());

        final OcrService ocrService = new OcrService();
        ocrService.process(new PdfToImageConverter().convert(Files.readAllBytes(testWsdl.toPath()), 0), regions.get(0));
        ocrService.process(new PdfToImageConverter().convert(Files.readAllBytes(testWsdl.toPath()), 0), regions.get(1));
        ocrService.process(new PdfToImageConverter().convert(Files.readAllBytes(testWsdl.toPath()), 0), regions.get(2));
        ocrService.process(new PdfToImageConverter().convert(Files.readAllBytes(testWsdl.toPath()), 0), regions.get(3));
        ocrService.process(new PdfToImageConverter().convert(Files.readAllBytes(testWsdl.toPath()), 0), regions.get(4));
    }








}