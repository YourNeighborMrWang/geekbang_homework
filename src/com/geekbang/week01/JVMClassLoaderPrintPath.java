package com.geekbang.week01;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * @program: demo
 * @description: 类加载
 * @author: Zhixing.Wang
 * @create: 2021-03-21 13:54
 **/
public class JVMClassLoaderPrintPath {
    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urLs) {
            System.out.println("====>" +  url.toExternalForm());
        }

        printClassLoader("扩展类加载器", JVMClassLoaderPrintPath.class.getClassLoader().getParent());
        printClassLoader("应用类加载器", JVMClassLoaderPrintPath.class.getClassLoader());

    }

    private static void printClassLoader(String name, ClassLoader classLoader) {
        System.out.println();

        if (classLoader != null) {
            System.out.println(name + " ClassLoader -> " + classLoader.toString());
            printURLForClassLoader(classLoader);
        } else {
            System.out.println(name + " ClassLoader -> null");
        }
    }


    private static void printURLForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList paths = (ArrayList) path;
        for (Object p : paths) {
            System.out.println("  ====>" + p.toString());
        }
    }

    private static Object insightField(Object obj, String fName) {
        Field f = null;
        try {
            if (obj instanceof ClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}
