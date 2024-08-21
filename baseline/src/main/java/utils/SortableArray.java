package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiFunction;

public class SortableArray<T> {
    private ArrayList<T> data;
    private HashMap<UUID, ArrayList<Integer>> orders;
    private UUID defaultOrder;
    private boolean editable = true;

    public SortableArray(){
        this.defaultOrder = UUID.randomUUID();
        this.data = new ArrayList<>();
        this.orders = new HashMap<>();
        this.orders.put(this.defaultOrder, new ArrayList<Integer>());
    }

    public void add(T entry){
        if(!editable) return;
        this.orders.get(this.defaultOrder).add(this.data.size());
        data.add(entry);
    }

    public UUID sort(BiFunction<T, T, Integer> func){
        this.editable = false;
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        for(int i=0; i<this.data.size();i++){
            tmp.add(i);
        }
        UUID tmpUuid = UUID.randomUUID();
        this.orders.put(tmpUuid, tmp);
        Collections.sort(tmp, new Comparator<Integer>() {
			@Override
			public int compare(Integer lhs, Integer rhs) {
				return func.apply(data.get(lhs), data.get(rhs));
			}
		});
        return tmpUuid;
    }

    public Iterator<T> getIterator(UUID uuid){
        return new Iterator<T>(this.orders.get(uuid), this.data);
    }

    public Iterator<T> getDefaultIterator(){
        return new Iterator<T>(this.orders.get(this.defaultOrder), this.data);
    }
}
