package com.mock.shared.infrastructure.google;


import com.mock.shared.domain.Injectable;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

@Injectable
public class ReflectionClasspaths {

    private final Reflections reflections;

    public ReflectionClasspaths() {
        this.reflections = new Reflections(new ConfigurationBuilder().forPackages("com.mock")
                                                                     .addUrls(ReflectionClasspaths.class.getProtectionDomain().getCodeSource().getLocation()));
    }

    public Set byTypeAnnotated(Class annotatedClass) {
        return reflections.getTypesAnnotatedWith(annotatedClass);
    }

    public Set bySubType(Class annotatedClass) {
        return reflections.getSubTypesOf(annotatedClass);
    }

    public Reflections all() {
        return reflections;
    }
}
