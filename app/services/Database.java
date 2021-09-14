package services;

import models.Contact;
import models.User;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class Database {
    private final ConcurrentHashMap<String, List<Contact>> contactByUserEmail;
    private final ConcurrentHashMap<String, User> userByUserEmail;

    public Database() {
        this.contactByUserEmail = new ConcurrentHashMap<>();
        this.userByUserEmail = new ConcurrentHashMap<>();
        userByUserEmail.put("user1@example.com", new User("user1@example.com", "password", 1));
        userByUserEmail.put("user2@example.com", new User("user2@example.com", "password", 2));
        userByUserEmail.put("user3@example.com", new User("user3@example.com", "password", 3));

        List<Contact> contacts;
        contacts = new ArrayList<>();
        contacts.add(new Contact("contact1", LocalDate.now()));
        contacts.add(new Contact("contact2", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact3", LocalDate.now().minusDays(1)));
        this.contactByUserEmail.put("user1@example.com", contacts);

        contacts = new ArrayList<>();
        contacts.add(new Contact("contact1", LocalDate.now()));
        contacts.add(new Contact("contact11", LocalDate.now()));
        contacts.add(new Contact("contact2", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact22", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact3", LocalDate.now().minusDays(1)));
        contacts.add(new Contact("contact33", LocalDate.now().minusDays(1)));
        this.contactByUserEmail.put("user2@example.com", contacts);

        contacts = new ArrayList<>();
        contacts.add(new Contact("contact1", LocalDate.now()));
        contacts.add(new Contact("contact11", LocalDate.now()));
        contacts.add(new Contact("contact111", LocalDate.now()));
        contacts.add(new Contact("contact2", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact22", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact222", LocalDate.now().plusDays(1)));
        contacts.add(new Contact("contact3", LocalDate.now().minusDays(1)));
        contacts.add(new Contact("contact33", LocalDate.now().minusDays(1)));
        contacts.add(new Contact("contact333", LocalDate.now().minusDays(1)));
        this.contactByUserEmail.put("user3@example.com", contacts);
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

    public void addContact(String userEmail, String name, LocalDate birthday) {
        List<Contact> contacts = contactByUserEmail.getOrDefault(userEmail, new ArrayList<>());
        contacts.add(new Contact(name, birthday));
        contactByUserEmail.put(userEmail, contacts);
    }

    public Contact getContact(String userEmail, String contactId) {
        return contactByUserEmail.getOrDefault(userEmail, new ArrayList<>())
                .stream()
                .filter(contact -> contact.getId().equalsIgnoreCase(contactId))
                .findFirst()
                .orElse(null);
    }

    public void editContact(String userEmail, String contactId, String name, LocalDate birthday) {
        List<Contact> contacts = contactByUserEmail.getOrDefault(userEmail, new ArrayList<>());
        for(Contact contact: contacts){
            if (contact.getId().equalsIgnoreCase(contactId)) {
                contact.setName(name);
                contact.setBirthday(birthday);
            }
        }
    }

    public void deleteContact(String userEmail, String contactId) {
        List<Contact> contacts = contactByUserEmail.getOrDefault(userEmail, new ArrayList<>());
        contacts.removeIf(contact -> contact.getId().equalsIgnoreCase(contactId));
    }
}
