package com.aiyo.admin.mapper;

import com.aiyo.admin.entity.SysDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 系统字典表 dictionary Mapper 接口
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Mapper
@Repository
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

}
