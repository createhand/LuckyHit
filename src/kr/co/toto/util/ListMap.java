package kr.co.toto.util;

import java.util.ArrayList;
import java.util.List;

public class ListMap {
    List<Item> list = new ArrayList<Item>();
    public void put(String key, String val) {
        list.add(new Item(key, val));
    }
    
    public Item get(int index) {
        return list.get(index);
    }
    public int size() {
        return list.size();
    }
    
    class Item {
        String key;
        String val;
        public Item(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }
}
