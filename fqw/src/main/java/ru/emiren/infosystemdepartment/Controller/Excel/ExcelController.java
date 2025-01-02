package ru.emiren.infosystemdepartment.Controller.Excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.Service.Excel.ExcelService;

@Controller
@RequestMapping("api/v2")
public class    ExcelController {
    private final ExcelService excelService;

    @Autowired
    public ExcelController(ExcelService excelService) { this.excelService = excelService; }

    @PostMapping("upload-excel")
    public String uploadExcel(MultipartHttpServletRequest request) {
        return excelService.uploadExcel(request);
    }
}
