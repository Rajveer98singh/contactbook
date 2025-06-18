package com.book.address.controller;

import com.book.address.dto.request.CreateContactRequest;
import com.book.address.dto.request.SearchContactRequest;
import com.book.address.dto.request.UpdateContactRequest;
import com.book.address.dto.response.ContactResponse;
import com.book.address.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/create")
    public ResponseEntity<List<ContactResponse>> create(
            @RequestBody @Valid List<CreateContactRequest> requests) {
        log.info("Received request to create {} contact(s).", requests.size());
        List<ContactResponse> response = contactService.createContacts(requests);
        log.info("Created {} contact(s) successfully.", response.size());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<List<ContactResponse>> update(
            @RequestBody @Valid List<UpdateContactRequest> requests) {
        log.info("Received update request for {} contact(s).", requests.size());
        List<ContactResponse> response = contactService.updateContacts(requests);
        log.info("Updated {} contact(s) successfully.", response.size());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Integer>> delete(@RequestBody List<String> ids) {
        log.info("Received delete request for {} contact ID(s).", ids.size());
        int deleted = contactService.deleteContacts(ids);
        log.info("Deleted {} contact(s).", deleted);
        return ResponseEntity.ok(Map.of("deleted", deleted));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ContactResponse>> search(
            @RequestBody @Valid SearchContactRequest query) {
        log.info("Received search request for query: '{}'", query.getQuery());
        List<ContactResponse> results = contactService.searchContacts(query.getQuery());
        log.info("Found {} contact(s) matching query '{}'.", results.size(), query.getQuery());
        return ResponseEntity.ok(results);
    }
}
