package com.tsystems.trainsProject.dto;

import java.util.ArrayList;
import java.util.List;

public class SingletonDto {
    private List<VariantDto> variants;

    private SingletonDto() {
        List<VariantDto> var = new ArrayList<>();
        this.variants = var;
    }

    private static SingletonDto instance;

    public static SingletonDto getInstance() {
        if (instance == null) {
            instance = new SingletonDto();
        }
        return instance;
    }

    public List<VariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDto> variants) {
        this.variants = variants;
    }
}
