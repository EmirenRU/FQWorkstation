package ru.emiren.infosystemdepartment.Service.Kafka.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Service.Kafka.KafkaConsumerService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    @Getter
    private List<StudentLecturersDTO> list = new ArrayList<>();
    private final Gson gson;

    @Autowired
    public KafkaConsumerServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @KafkaListener(topics = "info-system-sql-event", groupId = "group_id")
    public void consume(String message){
        System.out.println(message + "\n");
        Type listType = new TypeToken<List<StudentLecturersDTO>>() {}.getType();
        this.list = gson.fromJson(message, listType);
    }

}
