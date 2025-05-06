package ru.emiren.protocol.Service.Word.Impl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.emiren.protocol.DTO.Generator.ContestData;
import ru.emiren.protocol.Mapper.ContestMapper;
import ru.emiren.protocol.Service.Word.ContestService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ContestServiceImpl implements ContestService {

    @Override
    public NiceXWPFDocument generateWordDocument(InputStream is, Map<String, Object> data) {
        log.info("Size of array is {}", data.size());
        try {
            try {
                saveDataAsync(data);
            } catch (RestClientException e){
                log.warn("RestClientException: {}", e.getMessage());
            } catch (Exception e) {
                log.warn("Async Exception: {}", e.getMessage());
            }
            ContestData res = ContestMapper.toContestData(data);
//            document = document.merge(documents, document.getParagraphArray(0).getRuns().getFirst());
            return XWPFTemplate.compile(is, Configure.createDefault())
                    .render(res)
                    .getXWPFDocument();
        } catch (Exception e) {
            log.warn("WordService: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<String> processTable(XWPFTable table, int indexRow, int numCells) {
        return List.of();
    }


    @Override
    @Async
    public void saveDataAsync(Map<String, Object> dataMap) {

    }
}
