package gifse.thomas.com.br.gifse.remote.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Fixed_Height {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String sUrl) {
        this.url = sUrl;
    }
}
