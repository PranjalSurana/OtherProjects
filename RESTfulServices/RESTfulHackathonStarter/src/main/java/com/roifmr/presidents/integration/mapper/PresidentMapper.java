package com.roifmr.presidents.integration.mapper;

import com.roifmr.presidents.business.President;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PresidentMapper {

    List<President> getAllPresidents();
    President getPresident(@Param("id") int id);
    int deletePresident(@Param("id") int id);
    int updatePresident(President president);
    int insertPresident(President president);

}