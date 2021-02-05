package technicalblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicalblog.model.Post;
import technicalblog.repository.PostRepository;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public PostService() {
        System.out.println("*** PostService ***");
    }

    public List<Post> getAllPosts() {
        return repository.getAllPosts();
    }

    public Post getOnePost() {
        return repository.getLatestPost();
    }

    public void createPost(Post newPost) {
        newPost.setDate(new Date());
        repository.createPost(newPost);
        System.out.println("New Post: "+newPost.getId());
    }

    public Post getPost(Integer postId) {
        return repository.getPost(postId);
    }

    public void updatePost(Post updatedPost) {
        updatedPost.setDate(new Date());
        repository.updatePost(updatedPost);
    }

    public void deletePost(Integer postId) {
        repository.deletePost(postId);
    }

}
