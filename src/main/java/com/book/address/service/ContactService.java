package com.book.address.service;

import com.book.address.dto.request.CreateContactRequest;
import com.book.address.dto.request.UpdateContactRequest;
import com.book.address.dto.response.ContactResponse;
import com.book.address.exception.InvalidRequestException;
import com.book.address.model.Contact;
import com.book.address.storage.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository repository;

    public List<ContactResponse> createContacts(List<CreateContactRequest> requests) {
        log.info("Creating {} contact(s).", requests.size());

        try {
            List<Contact> contacts = requests.stream()
                    .map(req -> new Contact(req.getName(), req.getPhone(), req.getEmail()))
                    .toList();

            List<Contact> saved = repository.saveAll(contacts);

            log.info("Successfully created {} contact(s).", saved.size());
            return saved.stream().map(this::toResponse).toList();
        } catch (Exception e) {
            log.error("Error creating contacts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create contacts", e);
        }
    }

    public List<ContactResponse> updateContacts(List<UpdateContactRequest> updates) {
        log.info("Updating {} contact(s).", updates.size());

        try {
            List<Map<String, String>> updateList = new ArrayList<>();
            for (UpdateContactRequest u : updates) {
                if (u.getId() == null || u.getId().isEmpty()) {
                    log.warn("Update skipped: ID is missing.");
                    throw new InvalidRequestException("Contact ID must be provided for update.");
                }

                Map<String, String> map = new HashMap<>();
                map.put("id", u.getId());
                if (u.getName() != null) map.put("name", u.getName());
                if (u.getPhone() != null) map.put("phone", u.getPhone());
                if (u.getEmail() != null) map.put("email", u.getEmail());
                updateList.add(map);
            }

            List<Contact> updated = repository.updateContacts(updateList);
            log.info("Successfully updated {} contact(s).", updated.size());
            return updated.stream().map(this::toResponse).toList();
        } catch (Exception e) {
            log.error("Error updating contacts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update contacts", e);
        }
    }

    public List<ContactResponse> searchContacts(String query) {
        log.info("Searching contacts with query: '{}'", query);

        try {
            List<Contact> results = repository.searchByName(query);
            log.info("Found {} contact(s) matching '{}'.", results.size(), query);
            return results.stream().map(this::toResponse).toList();
        } catch (Exception e) {
            log.error("Error during search: {}", e.getMessage(), e);
            throw new RuntimeException("Search failed", e);
        }
    }

    public int deleteContacts(List<String> ids) {
        log.info("Deleting {} contact(s).", ids.size());

        if (ids.isEmpty()) {
            throw new InvalidRequestException("No IDs provided for deletion.");
        }

        try {
            int deleted = repository.deleteByIds(ids);
            log.info("Deleted {} contact(s) successfully.", deleted);
            return deleted;
        } catch (Exception e) {
            log.error("Error deleting contacts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete contacts", e);
        }
    }

    private ContactResponse toResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getPhone(),
                contact.getEmail()
        );
    }
}
