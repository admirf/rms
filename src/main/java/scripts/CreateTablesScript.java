package scripts;

import model.Tables;
import repository.DefaultSessionFactory;
import repository.TablesRepository;

import java.security.NoSuchAlgorithmException;

/**
 * A script to create the 11 tables of our restaurant
 */
public class CreateTablesScript {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        TablesRepository userRepo = new TablesRepository(DefaultSessionFactory.getInstance());

        for(int i = 0; i <= 10; ++i) {
            Tables table = new Tables();
            table.setName(i);
            table.setOccupied(false);
            userRepo.create(table);
        }

        DefaultSessionFactory.close();
    }
}
