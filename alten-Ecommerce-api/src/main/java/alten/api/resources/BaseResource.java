package alten.api.resources;

import alten.core.ApplicationProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
@Tag(name = "Base Resource API", description = "Base Resource API")
@AllArgsConstructor
@Slf4j
public class BaseResource {

    private ApplicationProperties applicationProperties;

    /**
     * GET /
     *
     * @return the ResponseEntity with status 200 (OK) and with body the app properties.
     */
    @Operation(summary = "Get the app properties", description = "Retrieves a list of all properties")
    @GetMapping("")
    public ResponseEntity getRoot() {
        log.info("[API : getRoot.DEBUT]");
        Map<String, String> body = new HashMap<>();
        body.put("version", this.applicationProperties.getVersion());
        body.put("env", this.applicationProperties.getEnvironnement());
        log.info("[API : getRoot.FIN]");
        return new ResponseEntity<>(body, null, HttpStatus.OK);
    }
}
