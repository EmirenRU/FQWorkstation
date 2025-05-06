package ru.emiren.protocol.DTO.Generator;

import com.deepoove.poi.data.*;
import lombok.Data;

@Data
public class ContestData {
    private String projectName;
    private String keywords;
    private NumberingRenderData scientificArea;
    private String orientation;
    private String category;
    private NumberingRenderData annotationGoals;
    private NumberingRenderData annotationRelevance;
    private NumberingRenderData annotationNovelty;
    private NumberingRenderData plannedScientificResults;
    private String mainEducationalOrScientificUnit;
}
