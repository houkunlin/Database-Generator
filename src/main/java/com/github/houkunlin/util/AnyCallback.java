package com.github.houkunlin.util;

/**
 * 用于执行任意方法的回调函数
 *
 * @author daiwenzh5
 * @since 2021-04-02 17:09
 */
@FunctionalInterface
public interface AnyCallback {
    Object call(Object... args);
}
