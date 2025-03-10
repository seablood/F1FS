package kr.co.F1FS.app.util;

public class AuthorCertification {
    public static boolean certification(String username, String author){
        return author.equals(username);
    }
}
