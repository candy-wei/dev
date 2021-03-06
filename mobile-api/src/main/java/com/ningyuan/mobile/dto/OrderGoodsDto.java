package com.ningyuan.mobile.dto;

public class OrderGoodsDto {
    private Long id;
    private String descript;
    private String detail;
    private String gallery;
    private Long idCategory;
    private Boolean isOnSale;
    private String name;
    private String pic;
    /**
     * 价格
     */
    private String price;
    /**
     * 数量
     */
    private Integer count;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDescript() { return descript; }

    public void setDescript(String descript) { this.descript = descript; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public String getGallery() { return gallery; }

    public void setGallery(String gallery) { this.gallery = gallery; }

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPic() { return pic; }

    public void setPic(String pic) { this.pic = pic; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public Integer getCount() { return count; }

    public void setCount(Integer count) { this.count = count; }
}
