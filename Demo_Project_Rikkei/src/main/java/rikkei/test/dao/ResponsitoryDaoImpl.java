package rikkei.test.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ResponsitoryDaoImpl
 *
 * @author NhatLKH
 *
 * @param <T> (kiểu dữ liệu của khóa chính trong class entity)
 * @param <I> (tên class entity)
 */
public class ResponsitoryDaoImpl<T, I extends Serializable> {

    private static final Log log = LogFactory.getLog(ResponsitoryDaoImpl.class);

    /** persistentClass **/
    private Class<T> persistentClass;

    /** sessionFactory **/
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    /**
     * ResponsitoryDaoImpl
     */
    @SuppressWarnings("unchecked")
    public ResponsitoryDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * save
     *
     * @param instance (thông tin các trường trong class entity)
     * @return đối tượng entity
     */
    @SuppressWarnings("unchecked")
    public I save(T instance) {
        log.debug("Saving " + persistentClass.getSimpleName() + " instance");
        try {
            I id = (I) sessionFactory.getCurrentSession().save(instance);
            log.debug("Save successful");
            return id;
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * update
     *
     * @param instance (thông tin các trường trong class entity)
     */
    public void update(T instance) {
        log.debug("Updating " + persistentClass.getSimpleName() + " instance");
        try {
            sessionFactory.getCurrentSession().update(instance);
            log.debug("Update successful");
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * delete
     *
     * @param instance (thông tin các trường trong class entity)
     */
    public void delete(T instance) {
        log.debug("Deleting " + persistentClass.getSimpleName() + " instance");
        try {
            sessionFactory.getCurrentSession().delete(instance);
            log.debug("Delete successful");
        } catch (RuntimeException re) {
            throw new RuntimeException();
        }
    }

    /**
     * findById
     *
     * @param id (id tự tăng)
     * @return đối tượng được lấy ra từ entity
     */
    @SuppressWarnings("unchecked")
    public T findById(I id) {
        log.debug("Getting " + persistentClass.getSimpleName() + " instance with id: " + id);
        try {
            T instance = (T) sessionFactory.getCurrentSession().get(persistentClass.getName(), id);
            if (instance == null) {
                log.debug("Get successful, no instance found");
            } else {
                log.debug("Get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new RuntimeException();
        }
    }


    public Query<T> createQuery(String queryString) {
        log.debug("Creating " + persistentClass.getSimpleName() + " query");
        try {
            Query<T> sqlQuery = sessionFactory.getCurrentSession().createQuery(queryString, persistentClass);
            log.debug("Create query successful");
            return sqlQuery;
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<T> findAll() {
        log.debug("Creating " + persistentClass.getSimpleName() + " query");
        try {
            Query<T> sqlQuery = sessionFactory.getCurrentSession().createQuery("FROM " + persistentClass.getName());
            log.debug("Create query successful");
            return sqlQuery.list();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new RuntimeException();
        }
    }
}
