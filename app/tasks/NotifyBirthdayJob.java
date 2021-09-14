package tasks;

import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Action;
import services.Database;
import services.MailServer;
import services.NotifyBirthday;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotifyBirthdayJob {
    private static Logger logger = LoggerFactory.getLogger(NotifyBirthdayJob.class);
    private final Database database;
    private final MailServer mailServer;
    private final NotifyBirthday notifyBirthday;


    @Inject
    public NotifyBirthdayJob(Database database, MailServer mailServer, NotifyBirthday notifyBirthday) {
        this.database = database;
        this.mailServer = mailServer;
        this.notifyBirthday = notifyBirthday;

        scheduleJob();
    }

    private void scheduleJob() {
        logger.info("scheduling notify birthday job...");
        try {
            ScheduledExecutorService scheduledExecutorService =
                    Executors.newScheduledThreadPool(5);

            scheduledExecutorService.scheduleAtFixedRate(this::sendBirthdayEmail,
                    0,
                    1,
                    TimeUnit.HOURS);
            logger.info("scheduling notify birthday job...done.");
        } catch (Throwable ex) {
            logger.error(ex.getCause().getMessage(), ex);
        }
    }

    private void sendBirthdayEmail() {
        try {
            List<User> users = database.findAllUser();
            for (User user : users) {
                logger.info("check contacts for user " + user.getEmail() + " ...");
                Action action = notifyBirthday.sendBirthdayEmail(user,
                        database.findContactByUserEmail(user.getEmail()),
                        LocalDateTime.now());
                action.execute(database, mailServer);
                logger.info("check contacts for user " + user.getEmail() + " ...done.");
            }
        } catch (Throwable ex) {
            logger.error(ex.getCause().getMessage(), ex);
        }
    }
}
