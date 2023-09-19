package views;

import java.sql.SQLException;

public interface IView {
    public void showInfo(Object ob);
    public void showAllInfo() throws SQLException;
    public Object inputInfo() throws SQLException;
}
