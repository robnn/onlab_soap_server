package model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper<T> {
    private ArrayList<T> list;

    public ListWrapper() {
        this.list = new ArrayList();
    }

    public ListWrapper(ArrayList<T> list) {
        this.list = list;
    }

    public ArrayList<T> getList() {
        return this.list;
    }
}