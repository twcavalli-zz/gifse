package gifse.thomas.com.br.gifse.remote.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Thomas on 11/07/2017.
 */

public class GifList {
    @SerializedName("data")
    private List<GifItem> gifItem;

    public List<GifItem> getTrending() {
        return gifItem;
    }

    public void setTrending(List<GifItem> gitItem) {
        gifItem = gitItem;
    }
}
