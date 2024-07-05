package saci.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloWorldController {

    @GetMapping
    public String hello() {
        return "Hello world";
    }

    @PostMapping
    public ResponseEntity<String> helloPost(@RequestBody String requestBody) {

        return ResponseEntity.ok("Received POST request with body: " + requestBody);
    }
}
