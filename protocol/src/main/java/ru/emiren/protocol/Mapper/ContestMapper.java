package ru.emiren.protocol.Mapper;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import lombok.extern.slf4j.Slf4j;
import ru.emiren.protocol.DTO.Generator.ContestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ContestMapper {

    public static ContestData toContestData(Map<String,Object> data){
        Style numberingAnnotationStyle = Style.builder().buildFontFamily("Times New Roman").buildFontSize(11.5).buildItalic().build();

        ContestData contestData = new ContestData();
        contestData.setProjectName(data.get("projectName").toString());
        contestData.setCategory(data.get("category").toString());
        contestData.setMainEducationalOrScientificUnit(data.get("scientificDivision").toString());
        contestData.setKeywords(String.join(",", ((List<String>) data.get("keywords"))));

        Style numberingSciAreaStyle = Style.builder().buildFontFamily("Times New Roman").buildFontSize(12).build();

        contestData.setScientificArea(handleList(numberingSciAreaStyle, data.get("scientificAreas")));
        contestData.setAnnotationNovelty(handleList(numberingAnnotationStyle, data.get("novelty")));
        contestData.setAnnotationRelevance(handleList(numberingAnnotationStyle, data.get("actuality")));
        contestData.setPlannedScientificResults(handleList(numberingAnnotationStyle, data.get("plannedResults")));
        contestData.setAnnotationGoals(handleList(numberingAnnotationStyle, data.get("goals")));
        //TODO check compatibility with typescript
        contestData.setProjectName(data.get("projectName").toString());
        contestData.setDescriptionOfMethods(data.get("descriptionOfMethods").toString());
        contestData.setFutureResults(data.get("futureResults").toString());
        contestData.setJustificationOfRequestedFinancing(data.get("justificationOfRequestedFinancing").toString());
        contestData.setFullNameOfSupervisor(data.get("fullNameOfSupervisor").toString());
        contestData.setListOfEstimatedCosts(data.get("listOfEstimatedCosts").toString());

        contestData.setResearchTeam(handleList(numberingAnnotationStyle, data.get("researchTeam")));
        return contestData;
    }

    private static NumberingRenderData handleList(Style style, Object data){
        if (data instanceof List<?>) {
            NumberingRenderData num = new NumberingRenderData(NumberingFormat.DECIMAL, new ArrayList<>());

            for (String obj : (List<String>) data) {
                num.getItems().add(new NumberingItemRenderData(0, Paragraphs.of(new TextRenderData(obj) {{
                    setStyle(style);
                }}).indentFirstLine(2).create()));
            }
            return num;
        }
        return null;
    }
}
