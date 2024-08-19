package com.github.houkunlin.config;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * 对象转换
 *
 * @author HouKunLin
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeanTransform {
    /**
     * 对象转换
     *
     * @param source 原始对象
     * @param to     target
     */
    void copyTo(Developer source, @MappingTarget Developer to);

    /**
     * 对象转换
     *
     * @param source 原始对象
     * @param to     target
     */
    void copyTo(Options source, @MappingTarget Options to);

    /**
     * 对象转换
     *
     * @param source 原始对象
     * @param to     target
     */
    void copyTo(Settings source, @MappingTarget Settings to);
}
