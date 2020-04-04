package com.ningyuan.route.dto;


import com.ningyuan.route.model.BgRouteModel;

import java.util.HashSet;
import java.util.Set;

public class BgMenuDto extends BgRouteModel {

    private Set<BgMenuDto> childrens = new HashSet<>();

    public Set<BgMenuDto> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<BgMenuDto> childrens) {
        this.childrens = childrens;
    }

    @Override
    public boolean equals(Object o) {
        BgMenuDto dto = (BgMenuDto) o;
        if(this.id.equals(dto.getId())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
