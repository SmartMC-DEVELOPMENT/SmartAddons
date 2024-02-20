package us.smartmc.smartaddons.plugin;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader {

    public AddonClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return super.getResourceAsStream(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
