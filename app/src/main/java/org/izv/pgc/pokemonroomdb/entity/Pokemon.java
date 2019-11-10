package org.izv.pgc.pokemonroomdb.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pokemon")
public class Pokemon implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "numoficial")
    private int numoficial;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "height")
    private float height;

    @ColumnInfo(name = "weight")
    private float weight;

    @ColumnInfo(name = "ability")
    private String ability;

    @ColumnInfo(name = "url")
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumoficial() {
        return numoficial;
    }

    public void setNumoficial(int numoficial) {
        this.numoficial = numoficial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", numoficial=" + numoficial +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", ability='" + ability + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
