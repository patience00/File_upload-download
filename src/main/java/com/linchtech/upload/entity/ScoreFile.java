package com.linchtech.upload.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 107
 * @date: 2019/4/10 13:45
 * @description:
 * @Review:
 */
@Data
@TableName("ScoreFile")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreFile implements Serializable {

    private static final long serialVersionUID = 2402616650592671182L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    @TableField("WID")
    private String wid;
    @TableField("Url")
    private String url;
    @TableField("Path")
    private String path;

    @TableField("Name")
    private String name;
}
