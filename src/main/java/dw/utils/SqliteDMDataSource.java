package dw.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqliteDMDataSource extends DriverManagerDataSource {

    @Override
    public void setUrl(String url) {
        String real = "jdbc:sqlite:" + Constants.RootPath() + url;
        real = real.replaceAll("%20", " ");
        super.setUrl(real);
    }

}