package ru.emiren.infosystemdepartment.Service.Kafka;


import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;

import java.util.List;

public interface KafkaConsumerService  {
    public void consume(String message);
    public List<StudentLecturersDTO> getList();
}
