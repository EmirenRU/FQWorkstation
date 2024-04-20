package ru.emiren.infosystemdepartment.Repository.Support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.emiren.infosystemdepartment.Model.Support.Data;

public interface DataRepository extends JpaRepository<Data, Long> {

}
