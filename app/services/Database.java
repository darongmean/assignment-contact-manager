package services;

import models.Contact;
import models.User;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class Database {
    private final ConcurrentHashMap<String, List<Contact>> contactByUserEmail;
    private final ConcurrentHashMap<String, User> userByUserEmail;

    public Database() {
        this.contactByUserEmail = new ConcurrentHashMap<>();
        this.userByUserEmail = new ConcurrentHashMap<>();
        userByUserEmail.put("user1@example.com", new User("user1@example.com", 1));

        List<Contact> user1Contacts = new ArrayList<>();
        user1Contacts.add(new Contact("user1@example.com", "contact1", LocalDate.now()));
        user1Contacts.add(new Contact("user1@example.com", "contact2", LocalDate.now().plusDays(1)));
        user1Contacts.add(new Contact("user1@example.com", "contact3", LocalDate.now().minusDays(1)));
        this.contactByUserEmail.put("user1@example.com", user1Contacts);
    }

    public List<Contact> findContactByUserEmail(String userEmail) {
        return contactByUserEmail.getOrDefault(userEmail, new ArrayList<>());
    }

    public User getUserByEmail(String email) {
        return userByUserEmail.get(email);
    }

    public void updateUserPreference(String userEmail, int newHourBeforeSendBirthdayEmail) {
        User user = userByUserEmail.get(userEmail);
        user.setHourBeforeSendBirthdayEmail(newHourBeforeSendBirthdayEmail);
        userByUserEmail.put(userEmail, user);
    }

    public List<User> findAllUser() {
        return new ArrayList<>(userByUserEmail.values());
    }
}
