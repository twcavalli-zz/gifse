package gifse.thomas.com.br.gifse.data.model;

import java.util.Date;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Gif {
    private String url;
    private Date when;

    public String getUrl() {
        return url;
    }

    public void setUrl(String sUrl) {
        this.url = sUrl;
        updateDate();
    }

    public void updateDate() {
        this.when = new Date();
    }
}
