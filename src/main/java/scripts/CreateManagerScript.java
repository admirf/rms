package scripts;

import model.User;
import repository.DefaultSessionFactory;
import repository.UserRepository;
import utility.Hasher;

import java.security.NoSuchAlgorithmException;

/**
 * Created by admir on 10.12.2016..
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
