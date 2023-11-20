package util;

import java.util.ArrayList;

public class FixedSizeList<E> extends ArrayList<E> {
    private int maxSize;

    public FixedSizeList(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(E e) {
        if (size() >= maxSize) {
                remove(0);
        }
        return super.add(e);
    }
}
