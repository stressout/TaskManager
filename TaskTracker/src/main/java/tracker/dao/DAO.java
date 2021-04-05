package tracker.dao;

import tracker.utils.DBHelper;

import java.sql.Connection;

public abstract class DAO<E> {

    public abstract void create(E entity);

    public abstract void delete(E entity);

    public abstract void showInfo(E entity);

    protected static final Connection connection = DBHelper.getConnection();
}
