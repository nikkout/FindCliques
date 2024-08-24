package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SortableArray<T> {
    private ArrayList<T> data;
    private HashMap<UUID, ArrayList<Integer>> orders;
    private UUID defaultOrder;
    private boolean editable = true;

    public SortableArray(){
        this(0);
    }

    public SortableArray(int size){
        this.defaultOrder = UUID.randomUUID();
        this.data = size == 0 ? new ArrayList<>() : new ArrayList<>(size);
        this.orders = new HashMap<>();
        this.orders.put(this.defaultOrder, size == 0 ? new ArrayList<Integer>() : new ArrayList<Integer>(size));
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

    public void forEach(Consumer<T> func){
        this.getDefaultIterator().forEach(func);
    }

    public boolean contains(T obj){
        return this.data.contains(obj);
    }
}
