package services;

import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;


public class MailServer {
    private final MailerClient mailerClient;

    @Inject
    public MailServer(MailerClient mailerClient) {
        this.mailerClient = mailerClient;
    }

    public void sendEmail(String toEmail, String message) {
        Email email = new Email()
                .setSubject("Birthday Alert")
                .setFrom("Mister FROM <from@example.com>")
                .addTo(toEmail)
                .setBodyText(message);
        mailerClient.send(email);
    }
}
