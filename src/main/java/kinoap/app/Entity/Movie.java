package kinoap.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private long duration;
    @Column(name = "avg_rating")
    private long avgRating;

    public Movie(String title, long duration, long avgRating) {
        this.title = title;
        this.duration = duration;
        this.avgRating = avgRating;
    }
}
