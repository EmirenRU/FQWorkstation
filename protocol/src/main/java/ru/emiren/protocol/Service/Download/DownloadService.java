package ru.emiren.protocol.Service.Download;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface DownloadService {
    public void generateAndSendFile(List<List<String>> data, HttpServletResponse response);
}
