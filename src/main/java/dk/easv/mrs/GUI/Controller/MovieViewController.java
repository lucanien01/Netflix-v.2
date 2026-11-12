package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    @FXML
    private TextField txtMovieCreateTitle, txtMovieCreateYear, txtMovieSearch;
    @FXML
    private ListView<Movie> lstMovies;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {

            txtMovieCreateTitle.setText(newValue.getTitle());
            txtMovieCreateYear.setText(newValue.getYear() + "");
            }
        ));
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void handleClickCreate(ActionEvent actionEvent) throws Exception {
        String mTitle = txtMovieCreateTitle.getText();
        int mYear = Integer.parseInt(txtMovieCreateYear.getText());
        try{
            movieModel.createMovie(new Movie(-1, mYear, mTitle));
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void handleClickUpdate(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null){
            selectedMovie.setTitle(txtMovieCreateTitle.getText());
            selectedMovie.setYear(Integer.parseInt(txtMovieCreateYear.getText()));

            try {
                movieModel.updateMovie(selectedMovie);
            } catch (Exception e) {
                displayError(e);
            }
        }
    }

    public void handleClickDelete(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

        lstMovies.getItems().remove(selectedMovie);
        if (selectedMovie != null){

            try {
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception e) {
                displayError(e);
            }
        }
    }
}
