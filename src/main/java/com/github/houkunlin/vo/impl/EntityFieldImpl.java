package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntityField;
import lombok.Data;

/**
 * 实体类字段信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Data
public class EntityFieldImpl implements IEntityField {
    private String name;
    private String nameFirstLower;
    private String nameFirstUpper;
    private String comment;
    private String typeName;
    private String fullTypeName;
    private boolean primaryKey;
    private boolean selected;
}
