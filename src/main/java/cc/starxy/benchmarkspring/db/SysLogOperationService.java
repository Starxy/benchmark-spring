
package cc.starxy.benchmarkspring.db;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 操作日志
 * @author sdmq
 * @since 1.0.0
 */
public interface SysLogOperationService {

    boolean save(SysLogOperationEntity entity);
	IPage<SysLogOperationEntity> page();
}