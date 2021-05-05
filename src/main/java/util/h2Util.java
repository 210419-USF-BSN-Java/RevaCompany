package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public class h2Util {

    public static void setup() throws SQLException, FileNotFoundException {
        Connection c = ConnectionUtil.getConnectionH2();
        RunScript.execute(c, new FileReader("setup.sql"));

    }

    public static void teardown() throws SQLException, FileNotFoundException {
        Connection c = ConnectionUtil.getConnectionH2();
        RunScript.execute(c, new FileReader("teardown.sql"));

    }
}