package gifse.thomas.com.br.gifse.data.model;

import java.util.Date;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Search {
    private String word;
    private Date when;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        updateDate();
    }

    public void updateDate() {
        this.when = new Date();
    }
}
