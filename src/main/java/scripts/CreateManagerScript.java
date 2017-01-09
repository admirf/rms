package scripts;

import model.User;
import repository.DefaultSessionFactory;
import repository.UserRepository;
import utility.Hasher;

import java.security.NoSuchAlgorithmException;

/**
 * A script used to create the root user aka Manager
 */
public class CreateManagerScript {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        UserRepository userRepo = new UserRepository(DefaultSessionFactory.getInstance());
        User manager = new User();
        manager.setName("Admir");
        manager.setSurname("Ferhatovic");
        manager.setEmail("root");
        manager.setPassword(Hasher.toMD5("toor"));
        userRepo.create(manager);
        DefaultSessionFactory.close();
    }
}
