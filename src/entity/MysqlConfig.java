package entity;

public class MysqlConfig {
    private String URL;
    private String DataBase;
    private String U;
    private String P;
    private String driver;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public MysqlConfig() {
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDataBase() {
        return DataBase;
    }

    public void setDataBase(String dataBase) {
        DataBase = dataBase;
    }

    public String getU() {
        return U;
    }

    public void setU(String u) {
        U = u;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }
}
