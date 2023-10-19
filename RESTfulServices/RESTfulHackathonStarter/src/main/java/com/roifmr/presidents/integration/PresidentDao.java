package com.roifmr.presidents.integration;

import com.roifmr.presidents.business.President;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PresidentDao {

    List<President> getAllPresidents();
    President getPresident(@Param("id") int id);
    int deletePresident(@Param("id") int id);
    int updatePresident(President president);
    int insertPresident(President president);

}