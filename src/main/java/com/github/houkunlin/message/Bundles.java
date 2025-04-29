package com.github.houkunlin.message;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * 国际化工具
 *
 * @author daiwenzh5
 * @since 2.8.4
 */
public class Bundles extends AbstractBundle {

    @NonNls
    private static final String BUNDLE = "i18n.language";

    private static final Bundles INSTANCE = new Bundles();

    private Bundles() {
        super(BUNDLE);
    }

    /**
     * 获取国际化信息
     *
     * @param key    键
     * @param params 参数
     * @return 国际化信息
     */
    @NotNull
    public static @Nls String message(@NotNull @PropertyKey(
        resourceBundle = BUNDLE
    ) String key, @NotNull Object... params) {
        return INSTANCE.getMessage(key, params);
    }

}
