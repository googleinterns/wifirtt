package structs;

import java.util.ArrayList;
import java.util.List;

public class BssidState {

    private List<String> bssidList;

    public BssidState() {
        bssidList = new ArrayList<>();
    }

    public void addBssid(String bssid) {
        bssidList.add(bssid);
    }

    public void deleteBssid(int index) {
        bssidList.remove(index);
    }

    public void editBssid(int index, String newBssid) {
        bssidList.set(index, newBssid);
    }

}
