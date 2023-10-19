package com.roifmr.presidents.integration.mapper;

import com.roifmr.presidents.models.President;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PresidentMapper {

    List<President> getAllPresidents();
    President getPresidentById(int id);
    
}