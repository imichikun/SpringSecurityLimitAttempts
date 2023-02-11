package loginAttempts.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "takendate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime takenDate;

//    @ManyToOne
//    @JoinColumn(referencedColumnName = "id", name = "reader_id")
//    private Reader reader;

    public Book(String title, int year) {
        this.title = title;
        this.year = year;
    }

    @Transient
    private boolean isExpired;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public LocalDateTime getTakenDate() {
        return takenDate;
    }

//    public Reader getReader() {
//        return reader;
//    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTakenDate(LocalDateTime takenDate) {
        this.takenDate = takenDate;
    }

//    public void setReader(Reader reader) {
//        this.reader = reader;
//    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", takenDate=" + takenDate +
                ", isExpired=" + isExpired +
                '}';
    }
}