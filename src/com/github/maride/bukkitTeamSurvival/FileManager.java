package com.github.maride.bukkitTeamSurvival;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

	public static void save(Object object,String path) throws Exception {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(object);
			oos.flush();
		}
		finally {
			if(oos != null) {
				oos.close();
			}
		}
	}
	
	public static Object load(String path) throws Exception {
		ObjectInputStream	ois		= null;
		Object				object	= null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			object = ois.readObject();
		}
		finally {
			if(ois != null) {
				ois.close();
			}
		}
		return object;
	}
}
