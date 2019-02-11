package org.ing.hackathon.totalrecall.docprocessor.model.parser;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.HashMap;
import java.util.Map;

@Builder
@ToString
public class ParsedResults {
    @Getter
    @Builder.Default
    private Map<String, ParsedResult> parseResults = new HashMap<>();

    @Tolerate
    public ParsedResults() {
    }

    public void addParseResult(final String name, final ParsedResult parsedResult) {
        parseResults.put(name, parsedResult);
    }
}
