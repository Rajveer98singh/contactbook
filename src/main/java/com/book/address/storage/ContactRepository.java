package com.book.address.storage;

import com.book.address.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ContactRepository {
    private final Map<String, Contact> db = new ConcurrentHashMap<>();

    public List<Contact> saveAll(List<Contact> contacts) {
        contacts.forEach(c -> db.put(c.getId(), c));
        return contacts;
    }

    public List<Contact> updateContacts(List<Map<String, String>> updates) {
        List<Contact> result = new ArrayList<>();
        for (Map<String, String> u : updates) {
            String id = u.get("id");
            Contact existing = db.get(id);
            if (existing != null) {
                if (u.containsKey("name")) existing.setName(u.get("name"));
                if (u.containsKey("phone")) existing.setPhone(u.get("phone"));
                if (u.containsKey("email")) existing.setEmail(u.get("email"));
                result.add(existing);
            }
        }
        return result;
    }

    public int deleteByIds(List<String> ids) {
        int count = 0;
        for (String id : ids) {
            if (db.remove(id) != null) count++;
        }
        return count;
    }

    public List<Contact> searchByName(String query) {
        return db.values().stream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}