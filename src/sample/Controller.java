package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Connection connection = null;
    @FXML public TableView<Movie> table;
    @FXML public TableColumn<Movie,Integer> idCol;
    @FXML public TableColumn<Movie,String> titleCol;
    @FXML public TableColumn<Movie,String> yearCol;
    @FXML public TableColumn<Movie,String> dCol;
    @FXML public TextField titleEdit;
    @FXML public TextField yearEdit;
    @FXML public TextField dEdit;

    private void setConnection(){             // just standard stuff going on here
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "webuser");
            System.out.println("OK");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private ObservableList<Movie> getData(){
        ObservableList<Movie> movies  = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * from movies",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String year = resultSet.getString("year");
                String duration = resultSet.getString("duration");
                movies.add(new Movie(id,title,year,duration));
            }
        }
        catch (SQLException e){
            //
        }
        return movies;
    }
    public void delete() throws SQLException{
        int id = table.getSelectionModel().getSelectedItem().getId();
        PreparedStatement statement = connection.prepareStatement("delete from movies where id ="+id);
        statement.executeUpdate();
        System.out.println("ok"+id);
        draw();
    }
    public void add() throws SQLException{
        String title = titleEdit.getText();
        String year = yearEdit.getText();
        String duration = dEdit.getText();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO movies (id,title,year,duration) VALUES ('"+(getData().size()+1)+"','"+ title+"','"+year+"','"+ duration+"')");
        System.out.println(statement);
        statement.executeUpdate();
        System.out.println("OK");
        draw();
    }
    private void draw(){
        table.setItems(getData());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        dCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

        setConnection();
        draw();
    }
}
