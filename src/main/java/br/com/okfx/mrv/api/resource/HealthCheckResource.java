package br.com.okfx.mrv.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckResource {

    @GetMapping("/health/check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(new HashMap<String, Object>() {{ put("status", "UP"); }});
    }

}
