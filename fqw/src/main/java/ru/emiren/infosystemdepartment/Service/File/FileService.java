package ru.emiren.infosystemdepartment.Service.File;


public interface FileService {
    public boolean isDocFormat(String fileFormat);
    public boolean isExcelFormat(String fileFormat);
    public String getFileFormat(String fileName);
}
