package com.linchtech.upload.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.linchtech.upload.entity.ScoreFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: 107
 * @date: 2019/4/10 16:20
 * @description:
 * @Review:
 */
@Mapper
@Repository
public interface ScoreMapper extends BaseMapper<ScoreFile> {
}
