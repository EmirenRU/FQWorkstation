package ru.emiren.infosystemdepartment.Service.Support;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;

public interface DataService {
    Data saveData(DataDTO data);

    DataDTO getDataById(int id);

    String getDataById(int id, Model model);

    String sendSimpleEmailAndSaveData(@Valid DataDTO data, Model model);

    String returnMainPage(Model model);
}
