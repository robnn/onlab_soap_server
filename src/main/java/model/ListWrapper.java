package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

/**
 * Created by robin on 5/5/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper <T>{
    private ArrayList<T> list;

    public ListWrapper() {
        list = new ArrayList<T>();
    }

    public ListWrapper(ArrayList<T> list) {
        this.list = list;
    }

    public ArrayList<T> getList() {
        return list;
    }
}
