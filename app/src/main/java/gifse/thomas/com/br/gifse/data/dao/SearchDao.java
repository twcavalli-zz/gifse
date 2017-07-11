package gifse.thomas.com.br.gifse.data.dao;

import java.util.ArrayList;
import gifse.thomas.com.br.gifse.data.model.Search;
import io.realm.Case;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Thomas on 11/07/2017.
 */

public class SearchDao {
    Realm realm;
    public SearchDao(Realm realm) {
        this.realm = realm;
    }

    // Return all data from Search table
    public ArrayList<String> getAllSearches() {
        ArrayList<String> lsSearch = new ArrayList<>();
        for (Search gif : realm.where(Search.class).findAllSorted("when", Sort.DESCENDING)) {
            lsSearch.add(gif.toString());
        }
        return lsSearch;
    }

    // Insert word or update datetime
    public boolean insert(String word){
        Search item = realm.where(Search.class).equalTo("word",word, Case.INSENSITIVE).findFirst();
        try {
            realm.beginTransaction();
            if (item != null) {
                item.updateDate();
            } else {
                item = new Search();
                item.setWord(word);
                realm.copyToRealm(item);
            }
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
