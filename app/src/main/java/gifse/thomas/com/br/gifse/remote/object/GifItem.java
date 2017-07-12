package gifse.thomas.com.br.gifse.remote.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 11/07/2017.
 */

public class GifItem {
    @SerializedName("images")
    private Images images;

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
