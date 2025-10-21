package it.einjojo.polarrealms.event.dispatch;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Scans the classpath for classes annotated with @NetworkEvent and registers them in the event registry.
 */
@Slf4j
public class NetworkEventReader {
    private final EventRegistry eventRegistry;
    private final Reflections reflections;


    public NetworkEventReader(EventRegistry eventRegistry) {
        this(eventRegistry, NetworkEventReader.class.getClassLoader(), "it.einjojo.polarrealms.event");
    }

    public NetworkEventReader(EventRegistry eventRegistry, ClassLoader classLoader, String packageName) {
        this.eventRegistry = eventRegistry;
        this.reflections = new Reflections(packageName,
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forClassLoader(classLoader))
                        .setScanners(Scanners.TypesAnnotated, Scanners.SubTypes)
        );
    }

    /**
     * Scans the classpath for classes annotated with @NetworkEvent and registers them in the event registry.
     */
    public void bindAnnotatedClasses() {
        log.info("Scanning for annotated classes...");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(NetworkEvent.class);
        log.info("Found {} annotated classes", annotatedClasses.size());
        for (Class<?> annotatedClass : annotatedClasses) {
            Method method;
            try {
                method = annotatedClass.getDeclaredMethod("deserialize", byte[].class);
                if (!Modifier.isStatic(method.getModifiers())) {
                    log.error("Method deserialize in {} must be static", annotatedClass.getName());
                    continue;
                }
            } catch (NoSuchMethodException e) {
                log.error("""
                        Error while reading annotated class: {}.
                        Classes annotated with @NetworkEvent must have a method with the following signature:
                        public static {} deserialize(byte[] bytes).
                        """, annotatedClass.getName(), annotatedClass.getSimpleName());
                continue;
            }
            byte eventId = annotatedClass.getAnnotation(NetworkEvent.class).id();
            eventRegistry.bind(annotatedClass, eventId, (byteArray) -> {
                try {
                    return method.invoke(null, (Object) byteArray);
                } catch (Exception e) {
                    throw new RuntimeException("Error while invoking method deserialize in" + annotatedClass.getName(), e);
                }
            });
        }


    }
}
