package services;

import models.Contact;
import models.User;
import services.action.NoAction;
import services.action.SendEmail;

import java.util.List;
import java.util.stream.Collectors;

public class NotifyBirthday {

    public Action update(User me, List<Contact> contactList, String hourBeforeSendBirthdayEmail) {
        int newHourBeforeSendBirthdayEmail = Integer.parseInt(hourBeforeSendBirthdayEmail);

        if (me.getHourBeforeSendBirthdayEmail() == newHourBeforeSendBirthdayEmail) {
            return new NoAction();
        } else {
            List<Contact> toBeNotified = contactList.stream()
                    .filter(contact -> hasUpcomingBirthday(contact, newHourBeforeSendBirthdayEmail))
                    .collect(Collectors.toList());
            return new SendEmail(me.getEmail(), emailMessage(toBeNotified));
        }
    }

    public boolean hasUpcomingBirthday(Contact contact, int hours) {
        return true;
    }
    public String emailMessage(List<Contact> contactList) {
        String names = contactList.stream().map(Contact::getName).collect(Collectors.joining(","));
        return names + " have birthdays soon.";
    }
}
