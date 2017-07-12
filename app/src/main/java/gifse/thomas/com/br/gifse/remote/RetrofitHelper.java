package gifse.thomas.com.br.gifse.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thomas on 11/07/2017.
 */

public class RetrofitHelper {
    public static GifseRemote newGifseRemote() {
        Secrets secret = new Secrets();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(secret.getENDPOINT())
                .build();
        return retrofit.create(GifseRemote.class);
    }
}
