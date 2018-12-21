package foora.perevozka.com.choosecityview;

/**
 * Created by Alexandr.
 */
public class SpinnerItem {

    private String name;
    private int id;

    public SpinnerItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
