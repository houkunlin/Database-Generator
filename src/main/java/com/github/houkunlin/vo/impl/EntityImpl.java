package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntity;
import lombok.Data;

import java.util.Set;

/**
 * 实体类信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Data
public class EntityImpl implements IEntity {
    private String name;
    private String nameFirstLower;
    private String nameFirstUpper;
    private String comment;
    private Set<String> packages;
    private String packageString;
}
