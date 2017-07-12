package gifse.thomas.com.br.gifse.data.dao;

import java.util.ArrayList;
import java.util.Random;
import gifse.thomas.com.br.gifse.data.model.Gif;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Thomas on 11/07/2017.
 */

public class GifDao {
    Realm realm;
    public GifDao(Realm realm) {
        this.realm = realm;
    }
    // Return all data from Gif table
    public ArrayList<String> getAll() {
        ArrayList<String> lsGifs = new ArrayList<>();
        for (Gif gif : realm.where(Gif.class).findAllSorted("when", Sort.DESCENDING)) {
            lsGifs.add(gif.toString());
        }
        return lsGifs;
    }

    // Get random gif from Gif Table
    public String getRandomGif() {
        Random random = new Random();
        RealmResults<Gif> list = realm.where(Gif.class).findAll();
        if (list.size() > 1) {
            return list.get(random.nextInt(list.size())).toString();
        } else {
            return null;
        }
    }

    // Insert Gif
    public boolean insert(String sUrl){
        Gif item = realm.where(Gif.class).equalTo("url",sUrl, Case.INSENSITIVE).findFirst();
        try {
            if (item == null) {
                realm.beginTransaction();
                item = new Gif();
                item.setUrl(sUrl);
                realm.copyToRealm(item);
                realm.commitTransaction();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
