import java.util.*;

enum Role {
    ADMIN,
    AUTHOR
}

class User {
    private String username;
    private Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}

class BlogPost {
    private String title;
    private String content;
    private String author;

    public BlogPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void display() {
        System.out.println("Title: " + title);
        System.out.println("By: " + author);
        System.out.println(content);
        System.out.println("------------------------");
    }
}

class BlogController {
    private List<BlogPost> posts = new ArrayList<>();

    public void createPost(User user, String title, String content) {
        posts.add(new BlogPost(title, content, user.getUsername()));
        System.out.println("✅ Post created successfully!");
    }

    public void viewPosts() {
        if (posts.isEmpty()) {
            System.out.println("📭 No posts available.");
            return;
        }
        for (BlogPost post : posts) {
            post.display();
        }
    }

    public void deletePost(User user, String title) {
        Iterator<BlogPost> iterator = posts.iterator();
        while (iterator.hasNext()) {
            BlogPost post = iterator.next();
            if (post.getTitle().equalsIgnoreCase(title)) {
                if (user.getRole() == Role.ADMIN || post.getAuthor().equals(user.getUsername())) {
                    iterator.remove();
                    System.out.println("🗑️ Post deleted.");
                    return;
                } else {
                    System.out.println("⛔ Access denied. You can only delete your own posts.");
                    return;
                }
            }
        }
        System.out.println("❌ Post not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BlogController blogController = new BlogController();

        System.out.println("👋 Welcome to the Blogging Platform!");
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter role (ADMIN/AUTHOR): ");
        Role role;
        try {
            role = Role.valueOf(sc.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid role. Defaulting to AUTHOR.");
            role = Role.AUTHOR;
        }

        User currentUser = new User(username, role);

        while (true) {
            System.out.println("\n📋 Menu:");
            System.out.println("1. Create Post");
            System.out.println("2. View Posts");
            System.out.println("3. Delete Post");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter content: ");
                    String content = sc.nextLine();
                    blogController.createPost(currentUser, title, content);
                    break;
                case 2:
                    blogController.viewPosts();
                    break;
                case 3:
                    System.out.print("Enter title to delete: ");
                    String delTitle = sc.nextLine();
                    blogController.deletePost(currentUser, delTitle);
                    break;
                case 4:
                    System.out.println("👋 Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
