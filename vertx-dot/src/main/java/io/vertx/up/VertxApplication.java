package io.vertx.up;

import com.vie.exception.up.UpClassArgsException;
import com.vie.exception.up.UpClassInvalidException;
import com.vie.fun.HBool;
import com.vie.fun.HTry;
import com.vie.util.Instance;
import com.vie.util.log.Annal;
import io.vertx.up.annotations.Up;
import io.vertx.up.mirror.Anno;
import io.vertx.up.mirror.Pack;
import io.vertx.up.web.ZeroLauncher;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Vertx Application start information
 */
public class VertxApplication {

    private static final Annal LOGGER = Annal.get(VertxApplication.class);

    private transient final Class<?> clazz;
    private ConcurrentMap<String, Annotation> annotationMap = new ConcurrentHashMap<>();
    private transient Annotation vertxAnno;

    private VertxApplication(final Class<?> clazz) {
        // Must not null
        HBool.execUp(
                null == clazz,
                LOGGER,
                UpClassArgsException.class, getClass());
        this.clazz = clazz;
        this.annotationMap = Anno.get(clazz);

        // Must be invalid
        HBool.execUp(
                !this.annotationMap.containsKey(Up.class.getName()),
                LOGGER,
                UpClassInvalidException.class, getClass(), clazz.getName());
    }

    public static void run(final Class<?> clazz, final Object... args) {
        HTry.execUp(() -> {
            // Run vertx application.
            new VertxApplication(clazz).run(args);
        }, LOGGER);
    }

    public void run(final Object... args) {
        final Launcher launcher = Instance.singleton(ZeroLauncher.class);
        launcher.start(vertx -> {
            final Set<Class<?>> classes = Pack.getClasses(null);
            for (final Class<?> clazz : classes) {
                System.out.println(clazz);
            }
        });
    }
}
