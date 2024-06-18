package ru.emiren.infosystemdepartment.Service.Word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface WordService {

    public NiceXWPFDocument generateWordDocument() throws Exception;

}
