package com.github.houkunlin.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * 文件类型
 * <p>解决诸如entity、dao、service等不同代码层之间配置信息散乱的问题，使用统一的结构描述，以支持自定义类型的添加。</p>
 *
 * @author daiwenzh5
 * @since 2025/4/26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
public class FileType {
    String type;
    String suffix;
    String packageName;
    String ext;
    String path;
    boolean override;

    /**
     * 创建文件类型
     *
     * @param type        文件类型
     * @param suffix      后缀
     * @param packageName 包名
     * @return 文件类型
     */
    public static FileType of(String type, String suffix, String packageName) {
        return new FileType(type, suffix, packageName, null, null, true);
    }
}
