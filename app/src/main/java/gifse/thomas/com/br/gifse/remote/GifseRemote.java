package gifse.thomas.com.br.gifse.remote;

import java.util.Map;

import gifse.thomas.com.br.gifse.remote.object.GifList;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Thomas on 11/07/2017.
 */

public interface GifseRemote {

    //Remote for get trending Gifs
    @GET("trending")
    Observable<GifList> getTrending(@QueryMap Map<String, String> options);

    //Remote for get trending Gifs
    @GET("search")
    Observable<GifList> search(@QueryMap Map<String, String> options);
}
