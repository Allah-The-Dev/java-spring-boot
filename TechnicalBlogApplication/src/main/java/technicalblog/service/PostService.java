package technicalblog.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import technicalblog.model.Post;

@Service
public class PostService {

    private static ArrayList<Post> POSTS = new ArrayList<>();

    static {
        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setBody("Post Body 1");
        post1.setDate(new Date());

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setBody("Post Body 2");
        post2.setDate(new Date());

        Post post3 = new Post();
        post3.setTitle("Post 3");
        post3.setBody("Post Body 3");
        post3.setDate(new Date());

        POSTS.add(post1);
        POSTS.add(post2);
        POSTS.add(post3);
    }

    public ArrayList<Post> getAllPosts() {
        return POSTS;
    }

    public ArrayList<Post> getOnePost() {
        ArrayList<Post> posts = new ArrayList<>();

        Post post1 = new Post();
        post1.setTitle("This is your Post");
        post1.setBody("This is your Post. It has some valid content");
        post1.setDate(new Date());
        posts.add(post1);

        return posts;

    }

	public boolean createPost(Post newPost) {
        return POSTS.add(newPost);
	}
}