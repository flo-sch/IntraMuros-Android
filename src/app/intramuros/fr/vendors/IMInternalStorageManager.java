package app.intramuros.fr.vendors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.util.Log;

public class IMInternalStorageManager {
	protected Context activityContext;
	
	public IMInternalStorageManager(Context activity) {
		this.activityContext = activity;
	}
	
	private static final String TAG = "IM-InternalStorage";

	public static void saveObjectToInternalStorage(Object resource, String objectName, Context activityContext) {
		Log.i(TAG, "will attempt to save : " + objectName);
		FileOutputStream _fos = null;
		ObjectOutputStream _oos = null;
		try {
			_fos = activityContext.openFileOutput(objectName, Context.MODE_PRIVATE);
			_oos = new ObjectOutputStream(_fos);
			_oos.writeObject(resource);
		}
		catch (FileNotFoundException FNFE) {
			FNFE.printStackTrace();
		}
		catch (IOException IOE) {
			IOE.printStackTrace();
		}
		finally {
			try {
				if (_oos != null) {
					_oos.close();
				}
				if (_fos != null) {
					_fos.close();
				}
			}
			catch (IOException IOE) {
				IOE.printStackTrace();
			}
		}
	}
	
	public static Object getObjectFromInternalStorage(String objectName, Context activityContext) {
		Log.i(TAG, "trying to load resource from InternalStorage : " + objectName);
		
		FileInputStream _fis = null;
		ObjectInputStream _ois = null;
		Object response = null;
		
		try {
			_fis = activityContext.openFileInput(objectName);
			_ois = new ObjectInputStream(_fis);
			response = _ois.readObject();
		}
		catch (FileNotFoundException FNFE) {
			FNFE.printStackTrace();
		}
		catch (StreamCorruptedException SCE) {
			SCE.printStackTrace();
		}
		catch (IOException IOE) {
			IOE.printStackTrace();
		}
		catch (ClassNotFoundException CNFE) {
			CNFE.printStackTrace();
		}
		finally {
			try {
				if (_ois != null) {
					_ois.close();
				}
				if (_fis != null) {
					_fis.close();
				}
			}
			catch (IOException IOE) {
				IOE.printStackTrace();
			}
		}
		
		return response;
	}
}
