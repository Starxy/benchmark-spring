package cc.starxy.benchmarkspring.aspect;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author sdmq
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    String value() default "";

	/**
	 * 服务代码
	 * @return
	 */
	String serviceCode() default "";

	/**
	 * 扩展信息
	 * @return
	 */
	String tag() default "";

}