package io.github.nullpoe.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerHome {

    private int limit;
    private List<HomeData> own;

    public PlayerHome() {
        this.limit = 0;
        this.own = new ArrayList<>();
    }
}
