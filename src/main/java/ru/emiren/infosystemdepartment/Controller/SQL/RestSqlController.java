package ru.emiren.infosystemdepartment.Controller.SQL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v2")
public class RestSqlController {

    // todo make json getter for models
    // todo make api for ajax

    private final Gson gson;

    @Autowired
    public RestSqlController(Gson gson) {
        this.gson = gson;
    }

    @GetMapping("refresh-db")
    public void refreshDb() {

    }
    
    @PostMapping("deserialization-data")
    public void deserialization(@RequestBody JsonObject request) {
        
    }
}
