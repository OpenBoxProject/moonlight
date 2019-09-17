package org.moonlightcontroller.registry;


import org.moonlightcontroller.bal.BoxApplication;

public class RegisteredBoxApplication {
    private String name;
    private String jarName;
    private BoxApplication application;

    public RegisteredBoxApplication(String jarName, BoxApplication application) {
        this.name = application.getName();
        this.jarName = jarName;
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public String getJarName() {
        return jarName;
    }

    public BoxApplication getApplication() {
        return application;
    }
}
