package model;

import lombok.Data;

import java.util.List;

@Data
public class Method {
    private List<MashTemp> mash_temp;
    private Fermentation fermentation;
    private String twist;
}