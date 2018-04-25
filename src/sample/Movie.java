package sample;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private Integer id;
    private SimpleStringProperty title,year,duration;

    Movie(Integer id, String title, String year, String duration){
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleStringProperty(year);
        this.duration = new SimpleStringProperty(duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}

