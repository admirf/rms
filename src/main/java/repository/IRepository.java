package repository;

/**
 * Repository interface
 */
public interface IRepository<T> {
    /**
     * @param t Object from which the entity should be created
     * @return ID of newly created Entity
     */
    Integer create(T t);

    /**
     * @param i ID of requested entity
     * @return The requested entity
     */
    T read(Integer i);

    /**
     * @param t Entity to be updated
     * @return ID of updated entity
     */
    Integer update(T t);

    /**
     * @param i ID of entity to be deleted
     * @return true if success, false otherwise
     */
    boolean delete(Integer i);
}
