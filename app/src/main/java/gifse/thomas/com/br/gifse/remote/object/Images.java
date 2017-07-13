package gifse.thomas.com.br.gifse.remote.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Images {
    @SerializedName("preview_gif")
    private Fixed_Height fixed_height;

    public Fixed_Height getFixed_height() {
        return fixed_height;
    }

    public void setFixed_height(Fixed_Height fixed_height) {
        this.fixed_height = fixed_height;
    }
}
