package org.learning.java;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nations";
        String user = "root";
        String password = "root";

        String query = """
                       SELECT c.name AS country_name, c.country_id, r.name AS region_name, cn.name AS continent_name
                       FROM countries c
                       JOIN regions r ON c.region_id = r.region_id
                       JOIN continents cn ON r.continent_id = cn.continent_id
                       WHERE c.name LIKE ?
                       ORDER BY c.name;
                       """;


        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Che Paese vuoi visitare? ");
                String param = scanner.nextLine();

                // Settaggio parametro di ricerca
                ps.setString(1, "%" + param + "%");
                scanner.close();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // prendo i valori della stringa
                        String countryName = rs.getString("country_name");
                        String countryId = rs.getString("country_id");
                        String regionName = rs.getString("region_name");
                        String continentName = rs.getString("continent_name");

                        System.out.println("Nome Nation: " + countryName);
                        System.out.println("ID Nation: " + countryId);
                        System.out.println("Nome Region: " + regionName);
                        System.out.println("Nome Continent: " + continentName);
                        System.out.println("___");
                    }
                }
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
