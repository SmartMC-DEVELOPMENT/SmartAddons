
package us.smartmc.smartaddons.plugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddonClassLoader extends URLClassLoader {

    private static AddonClassLoader addonClassLoader;

    public final static Map<AddonPlugin, String> pluginDirs = new HashMap<>();
    private final static Map<String, AbstractAddonInfo> modulesInfo = new HashMap<>();
    private final static Map<String, IAddonPlugin> loadedModules = new HashMap<>();

    public AddonClassLoader(URL[] urls) {
        super(urls, AddonPlugin.class.getClassLoader());
    }

    @Override
    protected void addURL(URL url) {
        super.addURL(url);
    }

    private static String loadAddonInfo(File file) throws Exception {
        URL jarUrl = file.toURI().toURL();
        URL[] urls = new URL[]{jarUrl};
        URLClassLoader jarLoader = new URLClassLoader(urls);
        JsonObject jsonObject = readJsonObject(jarLoader, "plugin.json");
        String mainClass = jsonObject.get("main").getAsString();
        String name = jsonObject.get("name").getAsString();
        JsonArray dependenciesArray = jsonObject.has("dependencies") ? jsonObject.getAsJsonArray("dependencies") : new JsonArray();
        List<String> dependencies = new ArrayList<>();
        for (JsonElement dependency : dependenciesArray) {
            dependencies.add(dependency.getAsString());
        }
        modulesInfo.put(name, new AbstractAddonInfo(file, mainClass, dependencies));
        return mainClass;
    }

    public static void loadModulesJars(File[] files) {
        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                try {
                    String infoId = loadAddonInfo(file);
                    System.out.println("Parsing file " + file.getName());
                    System.out.println("LOADMODULESJAR " + infoId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (String moduleName : modulesInfo.keySet()) {
            try {
                System.out.println("LOADMODULESJAR 2 " + moduleName);
                loadModuleWithDependencies(moduleName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadModuleWithDependencies(String moduleName) {
        if (loadedModules.containsKey(moduleName)) {
            return; // Already loaded
        }
        AbstractAddonInfo moduleInfo = modulesInfo.get(moduleName);
        System.out.println("TRIED TO OBTAIN ADDON INFO OF " + moduleName);
        for (String dependency : moduleInfo.getDependencies()) {
            loadModuleWithDependencies(dependency); // Ensure dependencies are loaded first
        }
        loadPluginJar(moduleInfo.getFile(), true);
    }


    public static void loadPluginJar(File file, boolean start) {
        try {
            URL jarUrl = file.toURI().toURL();
            AddonClassLoader mainLoader = getAddonClassLoader(new URL[]{jarUrl});

            URL[] urls = new URL[]{jarUrl};
            URLClassLoader jarLoader = new URLClassLoader(urls);
            JsonObject jsonObject = readJsonObject(jarLoader, "plugin.json");
            String mainClass = jsonObject.get("main").getAsString();

            // Cargar la clase principal desde el JAR usando el AddonClassLoader
            Class<?> loadedClass = mainLoader.loadClass(mainClass);

            // Verificar que implemente la interfaz IAddonPlugin
            if (!IAddonPlugin.class.isAssignableFrom(loadedClass)) {
                throw new IllegalArgumentException("La clase principal no implementa IAddonPlugin");
            }

            // Crear una instancia de la clase principal
            AddonPlugin plugin = (AddonPlugin) loadedClass.getDeclaredConstructor().newInstance();

            // Establecer la carpeta de datos
            Field dataFolderField = plugin.getClass().getSuperclass().getDeclaredField("dataFolder");
            dataFolderField.setAccessible(true);
            File dataFolder = new File(file.getParentFile(), plugin.getInfo().name());
            dataFolderField.set(plugin, dataFolder);
            dataFolderField.setAccessible(false);

            // Iniciar el plugin si es necesario
            if (start) {
                plugin.start();
            }

            // Registrar el módulo cargado
            loadedModules.put(mainClass, plugin);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el plugin desde el archivo JAR", e);
        }
    }

    private static JsonObject readJsonObject(URLClassLoader loader, String resourcePath) {
        InputStream jsonStream = loader.getResourceAsStream(resourcePath);
        if (jsonStream == null) {
            System.out.println("Resource not found: " + resourcePath);
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(jsonStream);
        return JsonParser.parseReader(inputStreamReader).getAsJsonObject();
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return super.getResourceAsStream(name);
    }

    private static AddonClassLoader getAddonClassLoader(URL[] urls) {
        if (addonClassLoader == null) {
            addonClassLoader = new AddonClassLoader(urls);
            return addonClassLoader;
        }
        for (URL url : urls) {
            addonClassLoader.addURL(url);
        }
        return addonClassLoader;
    }

}
