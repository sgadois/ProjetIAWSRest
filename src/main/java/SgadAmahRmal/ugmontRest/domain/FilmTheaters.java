package SgadAmahRmal.ugmontRest.domain;

import java.util.List;

/**
 * Created by mahamat on 16/04/15.
 */
public class FilmTheaters {
    private Film film;
    private List<Theater> theaterList;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Theater> getTheaterList() {
        return theaterList;
    }

    public void setTheaterList(List<Theater> theaterList) {
        this.theaterList = theaterList;
    }
}
