package gifse.thomas.com.br.gifse.remote.manager;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import gifse.thomas.com.br.gifse.remote.GifseRemote;
import gifse.thomas.com.br.gifse.remote.RetrofitHelper;
import gifse.thomas.com.br.gifse.remote.Secrets;
import gifse.thomas.com.br.gifse.remote.object.GifItem;
import gifse.thomas.com.br.gifse.remote.object.GifList;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Thomas on 11/07/2017.
 */

public class ApiDataManager {

    private final Context context;
    private final GifseRemote remote;
    private final Secrets secret;

    public ApiDataManager(Context context) {
        this.context = context;
        remote = RetrofitHelper.newGifseRemote();
        secret = new Secrets();
    }

    // Calls Gliphy API to get trendings.
    public Observable<String> getTrendings() {
        // Filters will be used on GET request
        Map<String, String> data = new HashMap<>();
        data.put("api_key", secret.getAPIKEY());
        data.put("limit", "25");
        data.put("rating", "G");

        return remote.getTrending(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<GifList, Observable<GifItem>>() {
                    @Override
                    public Observable<GifItem> call(GifList gifList) {
                        return Observable.from(gifList.getTrending());
                    }
                }).map(new Func1<GifItem, String>() {
                    @Override
                    public String call(GifItem gif) {
                        try {
                            return gif.getImages().getFixed_height().getUrl();
                        } catch (Exception e) { }
                        return null;
                    }
                });
    }

    // Calls Gliphy API to search.
    public Observable<String> search(String sWord) {
        // Filters will be used on GET request
        Map<String, String> data = new HashMap<>();
        data.put("api_key", secret.getAPIKEY());
        data.put("q", sWord);
        data.put("limit", "50");
        data.put("offset", "0");
        data.put("rating", "G");
        data.put("lang", "en");

        return remote.search(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<GifList, Observable<GifItem>>() {
                    @Override
                    public Observable<GifItem> call(GifList gifList) {
                        return Observable.from(gifList.getTrending());
                    }
                }).map(new Func1<GifItem, String>() {
                    @Override
                    public String call(GifItem gif) {
                        try {
                            return gif.getImages().getFixed_height().getUrl();
                        } catch (Exception e) { }
                        return null;
                    }
                });
    }
}
