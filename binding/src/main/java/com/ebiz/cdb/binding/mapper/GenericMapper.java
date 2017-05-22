package com.ebiz.cdb.binding.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 24/04/17.
 */
public interface GenericMapper<U, T> {

    /**
     * T from U mapper.
     *
     * @param u u.
     * @return t.
     */
    T from(U u);

    /**
     * U from T mapper.
     *
     * @param t t.
     * @return u.
     */
    U to(T t);

    /**
     * From U to T mapper method.
     * @param listIn listIn.
     * @return mapped collection list.
     */
    default List<U> toList(List<? extends T> listIn) {
        List<U> listOut = new ArrayList<>();
        for (T t : listIn) {
            listOut.add(to(t));
        }
        return listOut;
    }

    /**
     * From T to U mapper method.
     * @param listIn listIn.
     * @return mapped collection list.
     */
    default List<T> fromList(List<? extends U> listIn) {
        List<T> listOut = new ArrayList<>();
        for (U u : listIn) {
            listOut.add(from(u));
        }
        return listOut;
    }
}
