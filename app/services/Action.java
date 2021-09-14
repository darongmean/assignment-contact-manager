package services;

public interface Action {
    void execute(Database database, MailServer mailServer);
}
