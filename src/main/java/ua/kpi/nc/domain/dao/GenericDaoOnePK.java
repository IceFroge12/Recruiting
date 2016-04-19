package ua.kpi.nc.domain.dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IO on 19.04.2016.
 */
public interface GenericDaoOnePK<T, PK extends Serializable> {

    void create(T object);

    T read(PK key);

    void update(PK key);

    void delete(T object);

    List<T> getAll();

    T customRead(PreparedStatement preparedStatement);
}
