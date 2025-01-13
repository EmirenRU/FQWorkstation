package ru.emiren.auth.Service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;


public interface ScriptService {
    public ResponseEntity<Resource> getScript();
}
