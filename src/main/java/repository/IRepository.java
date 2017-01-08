package repository;

/**
 * Created by admir on 26.12.2016..
 */
public interface IRepository<T> {
    Integer create(T t);
    T read(Integer i);
    Integer update(T t);
    boolean delete(Integer i);
}
