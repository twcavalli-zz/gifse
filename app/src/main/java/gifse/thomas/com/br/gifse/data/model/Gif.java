package gifse.thomas.com.br.gifse.data.model;

import java.util.Date;
import io.realm.RealmObject;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Gif extends RealmObject {
    private String url;
    private Date when;

    public void setUrl(String sUrl) {
        this.url = sUrl;
        updateDate();
    }

    public void updateDate() {
        this.when = new Date();
    }

    public String toString() {
        return this.url;
    }
}
