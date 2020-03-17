package com.ningyuan.mobile.model;

import javax.persistence.*;

@Table( name ="t_shop_category_banner_rel" )
public class CategoryBannerRel {
    @Column(name = "id_category",columnDefinition = "BIGINT COMMENT '类别id'")
    private Long idCategory;
    @Column(name = "id_banner",columnDefinition = "BIGINT COMMENT 'banner id'")
    private Long idBanner;
}
