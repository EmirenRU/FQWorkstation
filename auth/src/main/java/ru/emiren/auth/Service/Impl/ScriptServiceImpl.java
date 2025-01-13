package ru.emiren.auth.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.emiren.auth.Service.ScriptService;

@Service
public class ScriptServiceImpl implements ScriptService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ScriptServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<Resource> getScript() {
        Resource resource = resourceLoader.getResource("classpath:static/js/auth.js");

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filepath=\"auth.js\"").contentType(MediaType.parseMediaType("application/javascript")).body(resource);
    }
}
