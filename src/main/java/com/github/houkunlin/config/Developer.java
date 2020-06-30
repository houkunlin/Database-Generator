package com.github.houkunlin.config;

import lombok.Data;

/**
 * 开发者信息
 *
 * @author HouKunLin
 * @date 2020/4/6 0006 21:00
 */
@Data
public class Developer {
    /**
     * 开发者姓名
     */
    private String author;
    /**
     * 开发者电子邮件
     */
    private String email;
}
