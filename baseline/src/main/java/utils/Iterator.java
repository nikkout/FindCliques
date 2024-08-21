package utils;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

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
        log.debug(""+this.pos);
        if(pos >= this.order.size()){
            return null;
        }
        this.pos++;
        return this.data.get(this.order.get(this.pos-1));
    }

    public void reset(){
        this.pos = 0;
    }
}
