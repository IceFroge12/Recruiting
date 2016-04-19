package ua.kpi.nc.domain.dao.impl.PostgreSQLImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.rmi.CodebaseAwareObjectInputStream;
import org.springframework.stereotype.Component;
import ua.kpi.nc.domain.dao.GenericDaoOnePK;
import ua.kpi.nc.domain.dao.impl.DaoSupport;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Created by IO on 19.04.2016.
 */
public abstract class PostgreSQLGenericDaoOnePKImpl<T, PK extends Serializable> implements GenericDaoOnePK<T, PK>  {

    protected Connection connection;

    public PostgreSQLGenericDaoOnePKImpl() {

    }

    public PostgreSQLGenericDaoOnePKImpl(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getCreateQuery();

    protected abstract String getReadQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement preparedStatement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement preparedStatement, T object) throws SQLException;

    protected abstract void additionActionForInsert(T object) throws SQLException;

    protected abstract PK getPKValue(T object);

    protected abstract String getPKAttribute();

    protected abstract Logger getLogger();

    @Override
    public void create(T object) {
        String sql = getCreateQuery();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            prepareStatementForInsert(preparedStatement, object);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public T read(PK key) {
        List<T> list = null;
        String sql = getReadQuery();
        sql  += "WHERE " + getPKAttribute() + " = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null == list || 0 == list.size()){
            throw new NoSuchElementException("No such element found");
        }else if (list.size() > 1){
            //TODO some exception;
        }
        return list.iterator().next();
    }

    @Override
    public void update(PK key) {

    }

    @Override
    public void delete(T object) {

    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T customRead(PreparedStatement preparedStatement) {
        List<T> list = null;
        try{
            ResultSet resultSet = preparedStatement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null == list || 0 == list.size()){
            throw new NoSuchElementException("No such element found");
        }else if (list.size() > 1){
            //TODO need make some exception;
        }
        return list.iterator().next();
    }

}
