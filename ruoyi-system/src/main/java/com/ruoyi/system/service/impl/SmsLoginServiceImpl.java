package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.user.ValidateCodeException;
import com.ruoyi.common.utils.JwtTokenUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.SnowflakeUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.SmsCodeUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.ClientUser;
import com.ruoyi.system.domain.vo.MobileLoginTokenVO;
import com.ruoyi.system.mapper.ClientUserMapper;
import com.ruoyi.system.service.ISmsLoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Lixiangping
 * @createTime 2022年03月10日 13:32
 * @decription: 短信验证码注册（登录）service实现类
 */
@Service
public class SmsLoginServiceImpl implements ISmsLoginService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JwtTokenUtils jwtToken;
    @Resource
    private ClientUserMapper clientUserMapper;

    @Override
    public AjaxResult loginMobile(String mobile, String verifyCode) {
        MobileLoginTokenVO tokenVO = new MobileLoginTokenVO();

        try {
            String smsCodeRedisKey = SmsCodeUtils.buildRedisSmsCodeKeyStr(mobile);
            Object o = redisTemplate.opsForValue().get(smsCodeRedisKey);
            if (o != null) {
                String codeInRedis = o.toString();
                if (!codeInRedis.equals(verifyCode)){//验证码不匹配
                    return AjaxResult.error("验证码输入有误~");
                }
                else if (codeInRedis.equals(verifyCode)){//正确
                    //清除redis中的验证码
                    redisTemplate.delete(smsCodeRedisKey);
                    //判断用户是否存在
                    ClientUser user = clientUserMapper.selectByMobile(mobile);
                    boolean isNewUser = false;
                    if (user == null) {//不存在，注册
                        user = new ClientUser();
                        user.setId(SnowflakeUtils.nextId());
                        user.setMobile(mobile);
                        user.setNickName(mobile);
                        user.setDelFlag(0);
                        user.setCreatedTime(new Date());
                        user.setCreatedBy("");
                        clientUserMapper.insertClientUser(user);

                        isNewUser = true;
                    }
                    //生成token
                    String token = jwtToken.getToken(user.getId().toString());
                    tokenVO.setToken(token);
                    tokenVO.setUserId(user.getId());
                    tokenVO.setNewUser(isNewUser);

                    return AjaxResult.success(tokenVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("登录异常");
        }
        return AjaxResult.error("登录异常");
    }

}
