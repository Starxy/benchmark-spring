package cc.starxy.benchmarkspring.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Mapper
public interface SysLogOperationDao extends BaseMapper<SysLogOperationEntity> {
	
}
