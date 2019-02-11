package org.ing.hackathon.totalrecall.docprocessor.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.ocr.DataRegion;
import org.ing.hackathon.totalrecall.docprocessor.model.parser.ParsedResults;
import org.ing.hackathon.totalrecall.docprocessor.service.ocr.OcrService;
import org.ing.hackathon.totalrecall.docprocessor.service.ocr.ParserService;
import org.ing.hackathon.totalrecall.docprocessor.service.ocr.PdfToImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/")
public class DocProcessorEndpoint {

    @Autowired
    public OcrService ocrService;

    @Autowired
    public PdfToImageConverter pdfToImageConverter;

    @Autowired
    public ParserService parserService;

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


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        final byte[] imageBytes = file.getBytes();
        final ParsedResults parsedResults = ParsedResults.builder().build();
        regions.forEach(
                region -> {
                    final String regionName = region.getName();
                    final String parsedText = ocrService.process(
                            pdfToImageConverter.convert(imageBytes, 0),
                            region);

                    parsedResults.addParseResult(
                            regionName,
                            parserService.parseRegionData(regionName, parsedText));
                }
        );

        log.info("Parsed Results: {}", parsedResults);

        return "redirect:/";
    }


}
