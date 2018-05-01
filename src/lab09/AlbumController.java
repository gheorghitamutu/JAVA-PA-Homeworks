package lab09;

import java.sql.*;

public class AlbumController {
    public void create(Integer artistID, String name, Integer releaseYear) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into albums (name, artist_id, release_year) values (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, artistID);
            pstmt.setInt(3, releaseYear);
            pstmt.executeUpdate();
        }
    }

    void list(Integer artistID) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select name from albums where artist_id='" + artistID + "'");
        while(rs.next()) System.out.println(rs.getString(1));

    }
}
