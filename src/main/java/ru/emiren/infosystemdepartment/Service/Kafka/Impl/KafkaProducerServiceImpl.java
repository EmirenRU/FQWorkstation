package ru.emiren.infosystemdepartment.Service.Kafka.Impl;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Service.Kafka.KafkaProducerService;

import java.util.List;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final static String sqlTopic = "info-system-sql-event";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

//    private final static String supportTopic = "info-system-support-event";

    @Autowired
    public KafkaProducerServiceImpl(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @Override
    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @Override
    public void sendMessageToSqlTopic(List<StudentLecturersDTO> stLeList) {
        String jsonMessage = gson.toJson(stLeList);
        kafkaTemplate.send(sqlTopic, jsonMessage);
    }

    @Override
    public void sendMessageToSupportTopic(List<DataDTO> dataList) {
        String jsonMessage = gson.toJson(dataList);
        kafkaTemplate.send(sqlTopic, jsonMessage);
    }
}
