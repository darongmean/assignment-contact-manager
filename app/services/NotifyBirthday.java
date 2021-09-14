package services;

import models.Contact;
import models.User;
import services.action.NoAction;
import services.action.SendEmail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class NotifyBirthday {

    public Action update(User me, List<Contact> contactList, LocalDateTime today, String hourBeforeSendBirthdayEmail) {
        int newHourBeforeSendBirthdayEmail = Integer.parseInt(hourBeforeSendBirthdayEmail);

        if (me.getHourBeforeSendBirthdayEmail() == newHourBeforeSendBirthdayEmail) {
            return new NoAction();
        }
        List<Contact> toBeNotified = contactList.stream()
                .filter(contact -> hasUpcomingBirthday(today, contact, newHourBeforeSendBirthdayEmail))
                .collect(Collectors.toList());

        if (toBeNotified.size() == 0) {
            return new NoAction();
        }

        return new SendEmail(me.getEmail(), emailMessage(toBeNotified));

    }

    public boolean hasUpcomingBirthday(LocalDateTime now, Contact contact, int hours) {
        if (isBirthday(now, contact)) {
            // too late, it's birthday already
            return false;
        }

        LocalDateTime nextHour = now.plusHours(hours).plusMinutes(15);
        return isBirthday(nextHour, contact) && nextHour.getHour() == 0;
    }

    private boolean isBirthday(LocalDateTime day, Contact contact) {
        Month nowMonth = day.getMonth();
        int nowDayOfMonth = day.getDayOfMonth();
        return nowMonth == contact.getBirthday().getMonth() && nowDayOfMonth == contact.getBirthday().getDayOfMonth();
    }

    public String emailMessage(List<Contact> contactList) {
        String names = contactList.stream().map(Contact::getName).collect(Collectors.joining(","));
        return names + " have birthdays soon.";
    }
}
