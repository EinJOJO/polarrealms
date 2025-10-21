package it.einjojo.polarrealms.event.dispatch;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NetworkEvent {
    byte id();
}
