package nl.novi.techiteasy.controllers;

import nl.novi.techiteasy.exceptions.RecordNotFoundException;
import nl.novi.techiteasy.exceptions.TelevisionNameTooLongException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/televisions")
public class TelevisionsController {
    List<String> televisionDataBase = new ArrayList<>();

    @GetMapping
    public ResponseEntity<String> getAllTelevisions() {
        return ResponseEntity.ok("Television Database list: " + televisionDataBase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTelevision(@PathVariable String id) {
        // Check if the index is valid
        int index = Integer.parseInt(id);
        if (index < 0 || index >= televisionDataBase.size()) {
            throw new RecordNotFoundException("Television with ID " + id + " not found");
        }

        String television = televisionDataBase.get(Integer.parseInt(id));
        return ResponseEntity.ok(television + " Id:" + id);
    }

    @PostMapping
    public ResponseEntity<String> addTelevision(@RequestBody String television) {
        if (television.length() > 20) {
            throw new TelevisionNameTooLongException("Television name is too long. Max 20 characters allowed");
        }
        televisionDataBase.add(television);
        return ResponseEntity.created(null).body("Television created: " + television);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTelevision(@PathVariable Long id, @RequestBody String television) {
        televisionDataBase.set(id.intValue(), television);

        return ResponseEntity.ok("Television with id " + id + " updated to: " + television);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable Long id) {
        televisionDataBase.remove(id.intValue());
        return ResponseEntity.noContent().build();
    }

}
