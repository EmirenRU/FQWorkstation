package ru.emiren.infosystemdepartment.Service.File.Impl;

import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Service.File.FileService;

import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final List<String> SUPPORTED_EXCEL_FORMATS = List.of("xlsx", "xls");
    private static final List<String> SUPPORTED_DOC_FORMATS = List.of("doc", "docx");

    public boolean isExcelFormat(String fileFormat) {
        return SUPPORTED_EXCEL_FORMATS.contains(fileFormat);
    }

    public boolean isDocFormat(String fileFormat) {
        return SUPPORTED_DOC_FORMATS.contains(fileFormat);
    }

    public String getFileFormat(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
