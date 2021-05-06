package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public class h2Util {

    public void setup() throws SQLException, FileNotFoundException {
        Connection c = ConnectionUtil.getConnectionH2();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("scripts/setup.sql")));
        RunScript.execute(c, reader);

    }

    public void teardown() throws SQLException, FileNotFoundException {
        Connection c = ConnectionUtil.getConnectionH2();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("scripts/teardown.sql")));
        RunScript.execute(c, reader);
    }
}