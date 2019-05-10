
import java.util.Date;
import java.util.HashSet;

public class Universe<E> extends HashSet<E> {
    private Date date;

    public Universe() {
        super();
        date = new Date();
    }
    public String initDate(){return date.toString();}
}
