package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.annotation.ManagedBean;

@ManagedBean
public class Feed {
	protected List<Post> posts;
	protected String feedFilename = "/Volumes/HDD/dmh/Downloads/feed.ser";
	protected File feedFile;

	@SuppressWarnings("unchecked")
	public Feed() {
		posts = Collections.synchronizedList(new ArrayList<Post>());
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

	public void addPost(Post post) throws IOException, PostException {
		if(post.checkEntries()) {
			posts.add(post);
			save();
		}
	}

	public List<Post> getPosts() {
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