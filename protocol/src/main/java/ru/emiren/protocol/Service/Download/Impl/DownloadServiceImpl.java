package ru.emiren.protocol.Service.Download.Impl;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.protocol.Service.Word.WordService;
import ru.emiren.protocol.Service.Download.DownloadService;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {
    @Autowired
    private WordService wordService;

    @Autowired
    private DateFormat dateFormat;

    @Override
    public void generateAndSendFile(List<List<String>> data, HttpServletResponse response) {
        NiceXWPFDocument doc = new NiceXWPFDocument();
        if (data.isEmpty()) {
            throw new IllegalStateException("No data available for download");
        }

        try (
                OutputStream out = response.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(out);
                )
        {
            doc = wordService.generateWordDocument(data);
            Date date = new Date();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"protocols-" + dateFormat.format(date) + ".docx\"");

            doc.write(bos);
            bos.flush();
        }   catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            PoitlIOUtils.closeQuietly(doc);

        }
    }

}
