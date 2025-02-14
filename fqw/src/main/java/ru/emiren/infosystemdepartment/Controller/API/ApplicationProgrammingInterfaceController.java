package ru.emiren.infosystemdepartment.Controller.API;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.Service.SQL.SqlService;
import ru.emiren.infosystemdepartment.Service.api.ApiService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApplicationProgrammingInterfaceController {

    private final ApiService apiService;
    private final SqlService sqlService;

    @Autowired
    public ApplicationProgrammingInterfaceController(ApiService apiService, SqlService sqlService) {
        this.apiService = apiService;
        this.sqlService = sqlService;
    }

    /**
     * Receive the SqlPayload for React by request
     *
     * @return a ResponseEntity with a status and FQW in Body
     */
    @GetMapping("/v1/receive_lecturers")
    public CompletableFuture<ResponseEntity<String>> receiveLecturers() {
        return sqlService.receiveLecturers().thenApply( reply -> {
            log.info("receive lecturers response");
            return reply;
        });
    }

    /**
     * Receive the FQW by a request from JS
     *
     * @param request contains a required data
     * @return a ResponseEntity with a status and FQW in Body
     */
    @GetMapping("/sql/receive_fqw")
    public CompletableFuture<ResponseEntity<String>> receiveFqw(HttpServletRequest request) {
        return sqlService.receiveThemes(request).thenApply(res -> {
            log.info("receive fqw response");
            return res;
        });
    }

    /**
     * handle a message from console
     * @param request contains a required data
     * @param response output for a client
     * @param message data from terminal
     * @return a ResponseEntity with a status
     */
    @PostMapping("/v1/upload_data_to_sql")
    public CompletableFuture<ResponseEntity<Object>> handleDataUpload(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 @RequestBody String message) {
        log.info("{}, {}", request.getAttribute("message"), message);
        return apiService.handleDataUpload(message).thenApply(
                res -> {
                    log.info("has completed handling data upload with message {}", message);
                    return res;
                });
    }

    @GetMapping("/v1/get-department-and-orientation/{studNumber}")
    public ResponseEntity<Map<String, String>> getMapOfDepartmentAndOrientation(@PathVariable String studNumber) {
        return sqlService.findDepartmentAndOrientationByStudNumber(studNumber);
    }

    @GetMapping("/js/receive/download-table")
    public ResponseEntity<String> receiveDownloadTable() {
        return apiService.returnJsFile("donwload_table.js");
    }

    @PostMapping("/v1/save-data")
    public ResponseEntity<String> saveData(@RequestBody Map<String, Object> data) {
        return sqlService.saveDataFromProtocol(data);
    }


    /**
     * Handler for selectors in /sql/lecturers
     * @param data a json with data from selectors
     * @return a Response
     */
    @PostMapping("/v1/receive-by-params")
    public CompletableFuture<ResponseEntity<String>> receiveByParams(@RequestBody Map<String, Object> data) {
        return sqlService.getLecturersAsync(data).thenApply( reply -> {
                log.info("receive by-params response with data {}", reply);
                return reply;
            }
        );
    }
}

