package org.ing.hackathon.totalrecall.docprocessor.service.ocr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ing.hackathon.totalrecall.docprocessor.model.parser.ParsedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParserService {

    private static final String regex = "SCRM8-\\d+";

    public ParsedResult parseRegionData(final String regionName, final String regionData) {
        final Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        final Matcher m = p.matcher(regionData);
        final List<String> results = new ArrayList<>();

        while (m.find()) {
            final String s = m.group(0);
            log.info("Found: {}", s);
            results.add(s);
        }

        return ParsedResult.builder()
                .name(regionName)
                .parsedResults(results)
                .build();
    }
}
