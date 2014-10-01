package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Feed {
	protected Vector<Post> posts = new Vector<Post>();
	protected String feedFilename = "/Volumes/HDD/dmh/Downloads/feed.ser";
	protected File feedFile;

	@SuppressWarnings("unchecked")
	public Feed() {
		feedFile = new File(feedFilename);
		if (feedFile.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(feedFile);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				posts = (Vector<Post>) objectIn.readObject();
				objectIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addEntry(Post post) throws IOException, PostException {
		if(post.checkEntries()) {
			posts.insertElementAt(post, 0);
			save();
		}
	}

	public Vector<Post> getEntries() {
		return posts;
	}

	private void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(feedFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(posts);
			objectOut.flush();
			objectOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
