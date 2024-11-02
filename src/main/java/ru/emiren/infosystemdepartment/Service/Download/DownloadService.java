package ru.emiren.infosystemdepartment.Service.Download;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.List;

public interface DownloadService {
    public void generateAndSendFile(List<List<String>> data, HttpServletResponse response);
}
