package org.isdp.vertx.boot.boot;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtil {
  public static List<Class> getClasses(String packageName) {
    List<Class> classes = new ArrayList<Class>();
    try {
      List<String> classNames = getClassNames(packageName);
      for (String className : classNames) {
        Class clazz = Class.forName(className);
        classes.add(clazz);
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return classes;
  }

  public static List<String> getClassNames(String packageName) {
    List<String> classNames = new ArrayList<String>();
    try {
      Set<String> classNameSet = getClassNames(packageName, true);
      classNames.addAll(classNameSet);
      Collections.sort(classNames);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return classNames;
  }

  private static Set<String> getClassNames(String packageName, boolean recursive) throws IOException {
    Set<String> fileNames = new HashSet<String>();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    String packagePath = packageName.replace(".", "/");
    Enumeration<URL> urls = loader.getResources(packagePath);
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      if (url != null) {
        String type = url.getProtocol();
        if (type.equals("file")) {
          fileNames.addAll(getClassNameByFile(url.getPath(), packagePath, recursive));
        } else if (type.equals("jar")) {
          fileNames.addAll(getClassNameByJar(url.getPath(), recursive));
        }
      }
    }
    fileNames.addAll(getClassNameByURLs(((URLClassLoader) loader).getURLs(), packagePath, recursive));
    return fileNames;
  }

  private static Set<String> getClassNameByFile(String filePath, String packagePath, boolean recursive) {
    Set<String> myClassName = new HashSet<String>();

    try {
      filePath = URLDecoder.decode(filePath, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    File[] childFiles = new File(filePath).listFiles();
    if ((childFiles == null) || (childFiles.length <= 0)) {
      return myClassName;
    }

    for (File childFile : childFiles) {
      if (childFile.isDirectory()) {
        if (recursive) {
          myClassName.addAll(getClassNameByFile(childFile.getPath(), packagePath, recursive));
        }
      } else {
        String childFilePath = childFile.getPath();
        childFilePath = childFilePath.replaceAll("\\\\", "/");
        if (childFilePath.endsWith(".class")) {
          int begIndex = childFilePath.indexOf(packagePath);
          int endIndex = childFilePath.lastIndexOf(".");
          if ((begIndex >= 0) && (endIndex >= 0) && (begIndex < endIndex)) {
            childFilePath = childFilePath.substring(begIndex, endIndex);
            childFilePath = childFilePath.replace("/", ".");
            myClassName.add(childFilePath);
          }
        }
      }
    }
    return myClassName;
  }

  private static Set<String> getClassNameByJar(String jarPath, boolean recursive) {
    Set<String> myClassName = new HashSet<String>();

    String[] jarInfo = jarPath.split("!");
    String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
    try {
      jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String packagePath = jarInfo[1].substring(1);

    try {
      JarFile jarFile = new JarFile(jarFilePath);
      Enumeration<JarEntry> entrys = jarFile.entries();
      while (entrys.hasMoreElements()) {
        String entryName = entrys.nextElement().getName();
        if (entryName.endsWith(".class")) {
          if (recursive) {
            if (entryName.startsWith(packagePath)) {
              entryName = entryName.replace("/", ".");
              entryName = entryName.substring(0, entryName.lastIndexOf("."));
              myClassName.add(entryName);
            }
          } else {
            int index = entryName.lastIndexOf("/");
            String myPackagePath = (index >= 0) ? entryName.substring(0, index) : entryName;
            if (myPackagePath.equals(packagePath)) {
              entryName = entryName.replace("/", ".");
              entryName = entryName.substring(0, entryName.lastIndexOf("."));
              myClassName.add(entryName);
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return myClassName;
  }

  private static Set<String> getClassNameByURLs(URL[] urls, String packagePath, boolean recursive) {
    Set<String> myClassName = new HashSet<String>();
    if ((urls != null) && (urls.length > 0)) {
      for (URL url : urls) {
        String urlPath = url.getPath();
        if (urlPath.endsWith("classes/")) {
          continue;
        } else if (urlPath.endsWith(".jar")) {
          myClassName.addAll(getClassNameByJar(urlPath + "!/" + packagePath, recursive));
        } else {
          myClassName.addAll(getClassNameByFile(urlPath, packagePath, recursive));
        }
      }
    }
    return myClassName;
  }
}