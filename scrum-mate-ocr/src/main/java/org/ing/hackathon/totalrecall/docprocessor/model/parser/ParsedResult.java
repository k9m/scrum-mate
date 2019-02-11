package org.ing.hackathon.totalrecall.docprocessor.model.parser;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ParsedResult {
    private String name;

    @Builder.Default
    private List<String> parsedResults = new ArrayList<>();

    @Tolerate
    public ParsedResult(){}
}
