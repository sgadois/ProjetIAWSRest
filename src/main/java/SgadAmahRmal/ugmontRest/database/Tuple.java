package SgadAmahRmal.ugmontRest.database;

/**
 * Created by mahamat on 07/04/15.
 */
public class Tuple<T, U>  {
    T name;
    U value;

    public Tuple(T name, U value) {
        this.name = name;
        this.value = value;
    }

    public  T getName(){ return name;}
    public U getValue(){ return value;}

}
