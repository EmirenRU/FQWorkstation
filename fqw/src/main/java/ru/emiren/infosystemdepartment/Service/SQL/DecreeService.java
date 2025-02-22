package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.Model.SQL.Decree;

public interface DecreeService {
    Decree findDecreeByThemeAndNumberOfDecreeAndStudNum(Long studNum, String theme, String numberOfDecree);

    Decree saveDecree(Decree decree);

    Long getMaxId();

    Decree findDecreeByTheme(String name);
}
