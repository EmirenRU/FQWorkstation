package ru.emiren.protocol.DTO.Generator;

import com.deepoove.poi.data.*;
import jakarta.validation.constraints.Size;
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
    // second part
    private String projectTasks;
    private String descriptionOfMethods;
    private String futureResults;
    private String justificationOfRequestedFinancing;
    private NumberingRenderData researchTeam;
    private String fullNameOfSupervisor;
    private String listOfEstimatedCosts;
    @Size(max = 10)
    private String Date;
    private String projectSupervisor;
    private String researchDirections;
    private String descriptionPlannedScientificResults;
}
