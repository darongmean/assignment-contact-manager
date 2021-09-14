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

    public void sendEmail() {
        Email email = new Email()
                .setSubject("Simple email")
                .setFrom("Mister FROM <from@email.com>")
                .addTo("Miss TO <to@email.com>")
                .setBodyText("A text message");
        mailerClient.send(email);
    }
}
