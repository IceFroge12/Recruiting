package ua.kpi.nc.domain.dao;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class DaoException extends Exception {
    public DaoException(){
        super();
    }

    public DaoException(String message, Throwable cause){
        super(message,cause);
    }

    public DaoException(String message){
        super(message);
    }
}
