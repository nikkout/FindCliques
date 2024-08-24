package utils;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import java.util.function.Consumer;

@Slf4j
public class Iterator<T> {
    private int pos = 0;
    private ArrayList<Integer> order;
    private ArrayList<T> data;

    public Iterator(ArrayList<Integer> order, ArrayList<T> data) {
        this.order = order;
        this.data = data;
    }

    public T getNext() {
        if(pos >= this.order.size()){
            return null;
        }
        this.pos++;
        return this.data.get(this.order.get(this.pos-1));
    }

    public T get(int pos) {
        if(pos >= this.order.size()){
            return null;
        }
        return this.data.get(this.order.get(pos));
    }

    public void reset(){
        this.pos = 0;
    }

    public void forEach(Consumer<T> func){
        int pre = this.pos;
        this.reset();
        T tmp = this.getNext();
        while(tmp != null){
            func.accept(tmp);
            tmp = this.getNext();
        }
        this.pos = pre;
    }

    public int size(){
        return this.data.size();
    }
}
