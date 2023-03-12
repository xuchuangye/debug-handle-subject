package com.mashibing.idempotence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.idempotence.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author xcy
 * @date 2023/3/11 - 8:42
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
