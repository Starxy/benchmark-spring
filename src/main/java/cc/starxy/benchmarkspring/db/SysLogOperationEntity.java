package cc.starxy.benchmarkspring.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 操作日志(SysLogOperation)表实体类
 *
 * @author sdmq
 * @since 2020-09-18 16:41:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_operation")
public class SysLogOperationEntity implements Serializable {
	private static final long serialVersionUID = 509804302100042000L;
	/**
	 *id主键
	 */
	@TableId(type = IdType.ASSIGN_ID) //主键ID_WORKER策略
	private Long id;
	/**
	 *模块名称，如：sys
	 */
	private String module;
	/**
	 *
	 */
	private String appId;
	/**
	 * 服务代码
	 */
	private String serviceCode;

	/**
	 * 日志标记
	 */
	private String tag;

	/**
	 *用户操作
	 */
	private String operation;
	/**
	 *请求URI
	 */
	private String requestUri;
	/**
	 *请求方式
	 */
	private String requestMethod;
	/**
	 *请求参数
	 */
	private String requestParams;
	/**
	 *请求时长(毫秒)
	 */
	private Long requestTime;

	private Long respondSize;
	/**
	 *用户代理
	 */
	private String userAgent;
	/**
	 *操作IP
	 */
	private String ip;
	/**
	 *状态  0：失败   1：成功
	 */
	private Integer status;
	/**
	 *用户名
	 */
	private String creatorName;
	/**
	 *创建者
	 */
	private String creator;
	/**
	 *创建时间
	 */
	private Date createDate;

}