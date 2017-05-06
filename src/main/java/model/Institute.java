package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * Created by robin on 2017. 03. 15..
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Institute implements Serializable{
    private Long id;
    private String name;

    public Institute() {
    }

    public Institute(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "name='" + name + '\'' +
                '}';
    }
}
