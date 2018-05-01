package lab09;

import java.sql.*;

public class ArtistController {
    public void create(String name, String country) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into artists (name, country) values (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, country);
            pstmt.executeUpdate();
        }
    }

    Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs= stmt.executeQuery("select id from artists where name='" + name + "'");
        return rs.next() ? rs.getInt(1) : null;
    }
}
