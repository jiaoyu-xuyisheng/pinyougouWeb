package com.jiaoyu.mapper;

import com.jiaoyu.pojo.Kelist;
import com.jiaoyu.pojo.KelistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KelistMapper {
    int countByExample(KelistExample example);

    int deleteByExample(KelistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Kelist record);

    int insertSelective(Kelist record);

    List<Kelist> selectByExample(KelistExample example);

    Kelist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Kelist record, @Param("example") KelistExample example);

    int updateByExample(@Param("record") Kelist record, @Param("example") KelistExample example);

    int updateByPrimaryKeySelective(Kelist record);

    int updateByPrimaryKey(Kelist record);
}