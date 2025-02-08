package ru.emiren.protocol.Service.Excel;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ExcelService {

    public String uploadExcel(MultipartHttpServletRequest request);
}
