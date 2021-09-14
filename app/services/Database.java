package services;

import models.Contact;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class Database {
    private final ConcurrentHashMap<String, List<Contact>> contactByUserEmail;

    public Database() {
        this.contactByUserEmail = new ConcurrentHashMap<>();

        List<Contact> user1Contacts = new ArrayList<>();
        user1Contacts.add(new Contact("user1@example.com", "contact1", LocalDate.now()));
        user1Contacts.add(new Contact("user1@example.com", "contact2", LocalDate.now().plusDays(1)));
        user1Contacts.add(new Contact("user1@example.com", "contact3", LocalDate.now().minusDays(1)));
        this.contactByUserEmail.put("user1@example.com", user1Contacts);
    }

    public List<Contact> findContactByUserEmail(String userEmail) {
        return contactByUserEmail.getOrDefault(userEmail, new ArrayList<>());
    }
}
