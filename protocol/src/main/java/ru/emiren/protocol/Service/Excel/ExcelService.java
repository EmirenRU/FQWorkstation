package ru.emiren.protocol.Service.Excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.protocol.DTO.TableData;

import java.util.List;

public interface ExcelService {

    public String uploadExcel(MultipartHttpServletRequest request);

    XSSFWorkbook generateExcelFile(List<TableData> res);
}
