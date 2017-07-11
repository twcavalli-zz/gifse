package gifse.thomas.com.br.gifse.data.model;

import java.util.Date;
import io.realm.RealmObject;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Search extends RealmObject  {
    private String word;
    private Date when;

    public void setWord(String word) {
        this.word = word;
        updateDate();
    }

    public void updateDate() {
        this.when = new Date();
    }

    public String toString() {
        return this.word;
    }

}
