package ru.emiren.infosystemdepartment.Service.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;

import java.util.List;

public interface KafkaProducerService {

    public void sendMessageToSqlTopic(List<StudentLecturersDTO> stLeList);
    public void sendMessageToSupportTopic(List<DataDTO> dataList);
    public KafkaTemplate<String, String> getKafkaTemplate();
}
