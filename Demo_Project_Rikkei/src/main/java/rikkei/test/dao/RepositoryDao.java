package rikkei.test.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.query.Query;

/**
 * RepositoryDao
 *
 * @author NhatLKH
 *
 * @param <T> (kiểu dữ liệu của khóa chính trong class entity)
 * @param <I> (tên class entity)
 */
public interface RepositoryDao<T, I extends Serializable> {

    I save(T instance);

    void update(T instance);

    void delete(T instance);

    T findById(I id);

    Query<T> createQuery(String queryString);

    public List<T> findAll();
}
