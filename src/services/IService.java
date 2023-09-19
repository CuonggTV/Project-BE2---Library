package services;

import java.sql.SQLException;

public interface IService {
    public void add() throws SQLException;
    public void read();
    public void update();
    public void delete(int id) throws SQLException;
}
