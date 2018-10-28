package org.moonlightcontroller.managers;

import org.moonlightcontroller.controller.MoonlightController;
import org.moonlightcontroller.exceptions.ApplicationsLoadException;
import org.moonlightcontroller.registry.ApplicationRegistry;
import org.moonlightcontroller.registry.IApplicationRegistry;

import java.io.IOException;

public class ApplicationsManager {

    private static ApplicationsManager instance = new ApplicationsManager();

    private MoonlightController moonlightController;

    public static ApplicationsManager getInstance() {
        return instance;
    }

    private ApplicationsManager() {
    }

    public void setMoonlightController(MoonlightController moonlightController) {
        this.moonlightController = moonlightController;
    }

    public void updateApps() throws ApplicationsLoadException {

        IApplicationRegistry reg = new ApplicationRegistry();
        try {
            reg.loadFromPath("./apps");
            this.moonlightController.updateApps(reg);

            // re-send processing graphs to locations
            ConnectionManager.getInstance().sendProcessingnGraphs();

        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationsLoadException(); // todo: move to Application registry
        }
    }
}
