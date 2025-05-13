package ru.emiren.protocol.Mapper;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import lombok.extern.slf4j.Slf4j;
import ru.emiren.protocol.DTO.Generator.ContestData;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ContestMapper {

    public static ContestData toContestData(Map<String,Object> data){
        log.info("ContestMapper toContestData with data: {}", data.toString());
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
        contestData.setProjectName(data.get("projectName").toString());
        contestData.setDescriptionOfMethods(data.get("descriptionOfMethods").toString());
        contestData.setFutureResults(data.get("futureResults").toString());
        contestData.setJustificationOfRequestedFinancing(data.get("justificationOfRequestedFinancing").toString());
        contestData.setFullNameOfSupervisor(data.get("fullNameOfSupervisor").toString());
        contestData.setListOfEstimatedCosts(handleEstimatedCostsObject(data.get("listOfEstimatedCosts")));
        contestData.setProjectSupervisor(data.get("projectSupervisor").toString());
        contestData.setDate(data.get("date").toString());
        contestData.setResearchDirections(data.get("researchDirections").toString());
        contestData.setResearchTeams(handleResearchTeam(numberingAnnotationStyle, data.get("researchTeams")));
        contestData.setProjectTasks(data.get("projectTasks").toString());
        contestData.setDescriptionPlannedScientificResults(data.get("descriptionPlannedScientificResults").toString());
        contestData.setDescriptionOfScientificFuture(data.get("descriptionOfScientificFuture").toString());
        return contestData;
    }

    private static NumberingRenderData handleResearchTeam(Style numberingAnnotationStyle, Object researchTeams) {
        if (researchTeams instanceof List<?>) {
            NumberingRenderData num = new NumberingRenderData(NumberingFormat.DECIMAL, new ArrayList<>());

            for (Object obj : (List<?>) researchTeams) {
                String res = null;
                if (obj instanceof Map) {
                    Map<String, String> data = (Map<String, String>) obj;
                    res = data.get("fullName") + " " +
                            data.get("workPlace") + " " +
                            data.get("position") + " " +
                            data.get("education") + " " +
                            data.get("speciality") + " " +
                            data.get("academicDegree") + " " +
                            data.get("academicTitle") + " " +
                            data.get("roleInProject") + " " +
                            data.get("experience");
                    log.info("result is {}", res);
                    num.getItems().add(new NumberingItemRenderData(0, Paragraphs.of(new TextRenderData(res) {{
                        setStyle(style);
                    }}).indentFirstLine(2).create()));
                }

            }
            return num;
        }
        return null;
    }

    private static String handleEstimatedCostsObject(Object listOfEstimatedCosts) {
        if (listOfEstimatedCosts instanceof List) {
            StringBuffer result = new StringBuffer();
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ROOT);
            decimalFormatSymbols.setDecimalSeparator(' ');
            DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);
            for (Object obj : (List<?>) listOfEstimatedCosts ) {
                if (obj instanceof Map) {
                    Map<String, String> map = (Map<String, String>) obj;

                    result.append(
                            (map.get("item") != null && !map.get("item").isEmpty()
                                    ? String.valueOf(map.get("item")).substring(0, 1).toUpperCase(Locale.ROOT) + String.valueOf(map.get("item")).substring(1)
                                    : ""
                            ) + " (" +
                                    String.valueOf(decimalFormat.format(map.get("total")))
                                    + " руб.)\n"
                    );
                }
            }
            return result.toString();
        }

        return null;
    }

    private static NumberingRenderData handleList(Style style, Object data){
        if (data instanceof List<?>) {
            NumberingRenderData num = new NumberingRenderData(NumberingFormat.DECIMAL, new ArrayList<>());

            for (Object obj : (List<String>) data) {
                num.getItems().add(new NumberingItemRenderData(0, Paragraphs.of(new TextRenderData(obj.toString()) {{
                    setStyle(style);
                }}).indentFirstLine(2).create()));
            }
            return num;
        }
        return null;
    }
}
