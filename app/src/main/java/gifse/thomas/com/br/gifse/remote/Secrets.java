package gifse.thomas.com.br.gifse.remote;

import android.util.Base64;

/**
 * Created by Thomas on 11/07/2017.
 */

public class Secrets {
    private static String ENDPOINT = "https://api.giphy.com/v1/gifs/";
    private String API_KEY = "9c85746c2a44496f9787e6cbaa4a5337";
    public String getENDPOINT() { return ENDPOINT; }
    public String getAPIKEY() { return API_KEY; }
}
