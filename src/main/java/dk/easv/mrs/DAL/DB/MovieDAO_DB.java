package dk.easv.mrs.DAL.DB;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public MovieDAO_DB() throws IOException {
    }

    @Override
    public List<Movie> getAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlGetMovies = "SELECT * FROM Movie";

            ResultSet rs = stmt.executeQuery(sqlGetMovies);

            while(rs.next()){

                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("Year");

                movies.add(new Movie(id, year, title));
            }
        }
        return movies;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        String sqlCreate = "INSERT INTO Movie (Title,Year) VALUES (?,?);";

        try (Connection conn = dbConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getYear());

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create movie object and send up the layers
            Movie createdMovie = new Movie(id, movie.getYear(), movie.getTitle());

            return createdMovie;
        }
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        String sqlUpdate = "UPDATE Movie SET Title = ?, Year = ? WHERE Id = ?";

        try (Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate)){

            // Bind parameters
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getYear());
            stmt.setInt(3, movie.getId());

            // Run the specified SQL statement
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

        String sqlDelete = "DELETE FROM Movie WHERE Id = ?";

        try (Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setInt(1, movie.getId());

            stmt.executeUpdate();
        }
    }
}