package tasks;

import com.google.inject.AbstractModule;

public class NotifyBirthdayJobBinder extends AbstractModule {

    protected void configure() {
        bind(NotifyBirthdayJob.class).asEagerSingleton();
    }

}
