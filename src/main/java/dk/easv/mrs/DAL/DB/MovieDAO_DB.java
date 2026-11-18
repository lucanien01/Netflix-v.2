package dk.easv.mrs.DAL.DB;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

            String sql = "SELECT * FROM Movie";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                float rating = rs.getFloat("Rating");
                int year = rs.getInt("Year");

                movies.add(new Movie(id, year, title));
            }
        }
        return movies;
    }

    @Override
    public Movie createMovie(Movie newMovie) throws Exception {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }
}