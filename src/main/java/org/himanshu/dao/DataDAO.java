package org.himanshu.dao;

import org.himanshu.db.MyConnection;
import org.himanshu.model.Data;

import java.awt.image.RescaleOp;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getAllFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from data where email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        List<Data> files = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String path = rs.getString(3);
            files.add(new Data(id, name, path));
        }
        return files;
    }
    public static int hideFile(Data file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();

        // Add file size check here
        File f = new File(file.getPath());
        long MAX_ALLOWED_SIZE = 1048576; // 1MB - adjust this value based on your bin_data column size

        if (f.length() > MAX_ALLOWED_SIZE) {
            throw new IOException("File too large to hide. Maximum allowed size is " + MAX_ALLOWED_SIZE + " bytes.");
        }

        PreparedStatement ps = connection.prepareStatement(
                "insert into data(Name_of_File, Path, Email, bin_data) values(?,?,?,?)");
        ps.setString(1, file.getFileName());
        ps.setString(2, file.getPath());
        ps.setString(3, file.getEmail());

        FileReader fr = new FileReader(f);
        ps.setCharacterStream(4, fr, f.length());

        int ans = ps.executeUpdate();
        fr.close();
        f.delete();
        return ans;
    }
    public static void unhide(int id) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select Path, bin_data from data where ID = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String path = rs.getString("Path");

        // Use binary stream for output
        InputStream is = rs.getBinaryStream("bin_data");
        FileOutputStream fos = new FileOutputStream(path);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
        is.close();

        ps = connection.prepareStatement("delete from data where ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Successfully Unhidden");
    }
}








