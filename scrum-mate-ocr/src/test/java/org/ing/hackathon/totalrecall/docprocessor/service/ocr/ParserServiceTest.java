package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import org.ing.hackathon.totalrecall.docprocessor.model.parser.ParsedResult;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParserServiceTest {

    private final ParserService parserService = new ParserService();

    @Test
    public void parseRegionData() {
        final ParsedResult parsedResult = parserService.parseRegionData("test", "hkjhjkhkjSCRM8-1hjkhjkSCRM8-2hjk");

        assertThat(parsedResult.getParsedResults().get(0), is("SCRM8-1"));
        assertThat(parsedResult.getParsedResults().get(1), is("SCRM8-2"));
    }
}