package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Properties;

public class SerializedObjectLoader<T> {
	
	public String loadFilename(String filenameProperty) {
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return prop.getProperty(filenameProperty);
	}
	
	public Object loadObjects(File serFile) {
		Object serializedObjects = new Hashtable<String, T>();
		if (serFile.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(serFile);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				serializedObjects = objectIn.readObject();
				objectIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serializedObjects;
	}
}
