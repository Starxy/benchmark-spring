package cc.starxy.benchmarkspring.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sdmq
 * @date 2020/9/18 16:50
 */
@Service
public class SysLogOperationServiceImpl extends ServiceImpl<SysLogOperationDao, SysLogOperationEntity>
		implements SysLogOperationService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysLogOperationEntity entity) {
        baseMapper.insert(entity);
        return true;
    }

	@Override
	public IPage<SysLogOperationEntity> page() {
		return  baseMapper.selectPage(new Page<>(1,10),null);
	}
}
