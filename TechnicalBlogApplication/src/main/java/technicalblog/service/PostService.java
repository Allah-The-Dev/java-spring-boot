package technicalblog.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import technicalblog.model.Post;

@Service
public class PostService {

    private final String url = "jdbc:postgresql://localhost:5432/technicalblog";
    private final String user = "postgres";
    private final String password = "password";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM posts");
            while (rs.next()) {
                Post post = new Post();
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                posts.add(post);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<Post> getOnePost() {
        ArrayList<Post> posts = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM posts WHERE id = 4");
            while (rs.next()) {
                Post post = new Post();
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                posts.add(post);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public long createPost(Post newPost) {

        String SQL = "INSERT INTO posts(title,body,date) "
                + "VALUES(?,?,?)";

        long id = 0;
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, newPost.getTitle());
            pstmt.setString(2, newPost.getBody());
            pstmt.setDate(3, new java.sql.Date(newPost.getDate().getTime()));


            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
	}
}