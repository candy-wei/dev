package com.ningyuan.route.daomapper.mapper;

import com.ningyuan.route.model.BgResourceModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * generated by Generate Mapper.groovy 
 * <p>Date: Wed Mar 20 14:42:20 CST 2019.</p>
 *
 * @author (zengrc)
 */

public interface BgResourceMapper extends Mapper<BgResourceModel> {

    List<BgResourceModel> getResourcesByRole(long id);

//    List<BgResourceRouteDto> getResourcesByRoute(long id);

}