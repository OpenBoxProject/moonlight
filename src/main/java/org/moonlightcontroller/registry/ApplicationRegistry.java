package org.moonlightcontroller.registry;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.moonlightcontroller.bal.BoxApplication;

/**
 * Application registry is responsible for loading applications into the OBC
 * It is an Implementation for the IApplicationRegistry interface.
 */
public class ApplicationRegistry implements IApplicationRegistry {

	private Map<String, BoxApplication> apps;
	
	public ApplicationRegistry(){
		this.apps = new HashMap<>();
	}
	@Override
	public void addApplication(BoxApplication app) {
		this.apps.put(app.getName(), app);
	}

    @Override
    public boolean loadFromPath(String path) throws IOException{
        File loc = new File(path);

        if (!loc.exists()) {
            System.out.println("Given path is not found:" + path);
            return false;
        }
        if (!loc.isDirectory()){
            System.out.println("Path is not a directory:" + path);
            return false;
        }

        File[] flist = loc.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getPath().toLowerCase().endsWith(".jar");
            }
        });


        URL[] urls = new URL[flist.length];
        for (int i = 0; i < flist.length; i++){
            urls[i] = flist[i].toURI().toURL();
        }
        URLClassLoader ucl = new URLClassLoader(urls);

        ServiceLoader<BoxApplication> sl = ServiceLoader.load(BoxApplication.class, ucl);
        Iterator<BoxApplication> apit = sl.iterator();
        while (apit.hasNext()){
            BoxApplication app = apit.next();
            System.out.printf("Registry: Found application: %s %n", app.getName());
            this.apps.put(app.getName(), app);
        }
        return true;
    }

	@Override
	public boolean loadFromClassPath(String path) throws IOException{

        ServiceLoader<BoxApplication> sl = ServiceLoader.load(BoxApplication.class, this.getClass().getClassLoader());
        Iterator<BoxApplication> apit = sl.iterator();
        while (apit.hasNext()){
            BoxApplication app = apit.next();
            System.out.printf("Registry: Found application: %s %n", app.getName());
            this.apps.put(app.getName(), app);
        }
		return true;
	}
	
	@Override
	public List<BoxApplication> getApplications() {
		List<BoxApplication> list = new ArrayList<>(this.apps.values());
		return list;
	}
	@Override
	public BoxApplication getApplicationByName(String name) {
		return this.apps.get(name);
	}
}
