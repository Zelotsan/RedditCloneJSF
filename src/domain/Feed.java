package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;

import util.SerializedObjectLoader;
import domain.exception.PostException;

@ManagedBean
public class Feed {
	protected List<Post> posts;
	protected String feedFilename;
	protected File feedFile;

	@SuppressWarnings("unchecked")
	public Feed() {
		SerializedObjectLoader<Post> loader = new SerializedObjectLoader<Post>();
		feedFile = new File(loader.loadFilename("feed-filename"));
		posts = (List<Post>) loader.loadObjects(feedFile);
		posts = Collections.synchronizedList(new ArrayList<Post>());
	}

	public void addPost(Post post) throws IOException, PostException {
		if(post.checkEntries()) {
			posts.add(post);
			save();
		}
	}
	public List<Post> getPosts() {
		return Collections.unmodifiableList(posts);
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
