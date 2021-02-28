package com.github.houkunlin.config;

import lombok.Data;

/**
 * 其他配置
 *
 * @author HouKunLin
 */
@Data
public class OtherConfig {
    /**
     * 在 2.4.0 版本往后升级的时候，决定是否提示迁移代码
     */
    private boolean showUpgradeMoveTip = true;
}
