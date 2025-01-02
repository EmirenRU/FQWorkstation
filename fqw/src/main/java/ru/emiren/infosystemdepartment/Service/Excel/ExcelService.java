package ru.emiren.infosystemdepartment.Service.Excel;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ExcelService {

    public String uploadExcel(MultipartHttpServletRequest request);
}
