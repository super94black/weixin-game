package org.cet.dao;

import org.apache.ibatis.annotations.Param;
import org.cet.pojo.mysql.Classtable;
import org.cet.pojo.mysql.ClasstableExample;


import java.util.List;

public interface ClasstableMapper {
    int countByExample(ClasstableExample example);

    int deleteByExample(ClasstableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Classtable record);

    int insertSelective(Classtable record);

    List<Classtable> selectByExample(ClasstableExample example);

    Classtable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Classtable record, @Param("example") ClasstableExample example);

    int updateByExample(@Param("record") Classtable record, @Param("example") ClasstableExample example);

    int updateByPrimaryKeySelective(Classtable record);

    int updateByPrimaryKey(Classtable record);
}