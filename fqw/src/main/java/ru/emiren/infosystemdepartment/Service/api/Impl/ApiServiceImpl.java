package ru.emiren.infosystemdepartment.Service.api.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.DTO.Payload.SelectorSqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.Service.SQL.SqlService;
import ru.emiren.infosystemdepartment.Service.api.ApiService;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {
    private final JdbcTemplate jdbcTemplate;
    private final SqlService sqlService;
    private final Gson gson;

    @Autowired
    ApiServiceImpl(@Qualifier("sqlJdbcTemplate") JdbcTemplate jdbcTemplate, SqlService sqlService, Gson gson){
        this.jdbcTemplate = jdbcTemplate;
        this.sqlService = sqlService;
        this.gson = gson;
    }



    @Override
    public ResponseEntity<String> returnJsFile(String s) {
        // TODO make later implementation of CDN
//        String js = resourceLoader.getResource("classpath:/static/js/"+s);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @Override
    public CompletableFuture<ResponseEntity<String>> receiveSelectorPayload(HttpServletRequest request) {
        SelectorSqlPayload payload = sqlService.receiveSelectors();
        if (payload == null) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(gson.toJson(payload)));
    }

    /**
     * Handles a message from console for CRUD operation
     *
     * @param message
     * @return a ResponseEntity with status of query
     */
    @Override
    @Async
    public CompletableFuture<ResponseEntity<Object>> handleDataUpload(String message) {
        Map<String, String> headers = new HashMap<>();
        headers.put("message", message);
        log.info("Message received: {}", message);
        try {
            if (message.toUpperCase().contains("SELECT")){
                headers.put("result", String.valueOf(jdbcTemplate.queryForList(message)));
            } else if (message.toUpperCase().contains("INSERT")){
                headers.put("result", "INSERT");
            } else if (message.toUpperCase().contains("UPDATE")){
                headers.put("result", "UPDATE");
            } else if (message.toUpperCase().contains("DELETE")){
                headers.put("result", "DELETE");
            } else {
                headers.put("result", "ERROR");
            }
        }  catch (BadSqlGrammarException e) {
            log.error("SQL Syntax Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        } catch (DataAccessException e) {
            log.error("Database Access Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        }
        if (headers.containsKey("error")) {
            headers.put("status", "500");
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headers));
        }
        headers.put("status", "200");
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(headers));
    }
}
