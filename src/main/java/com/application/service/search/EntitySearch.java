package com.application.service.search;

import com.application.entity.Entity;

import java.util.List;

public abstract class EntitySearch<T extends Entity>{
    List<T> entityList;

    protected EntitySearch(List<T> entityList){
        this.entityList = entityList;
    }

    public T getById (int identity){
        int left = 0;
        int right = entityList.size()-1;
        int search = -1;
        while (left <= right){
            int middle = (left+right)/2;
            if (entityList.get(middle).getId() == identity){
                search = middle;
                break;
            }
            if (identity < entityList.get(middle).getId()){
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        if (search != -1){
            return entityList.get(search);
        } else {
            return null;
        }
    }
}
