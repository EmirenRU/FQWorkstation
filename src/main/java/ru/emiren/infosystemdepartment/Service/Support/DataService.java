package ru.emiren.infosystemdepartment.Service.Support;

import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;

public interface DataService {
    Data saveData(DataDTO data);
}
