package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ClientUserMapper;
import com.ruoyi.system.service.IClientUserService;

/**
 * 客户端用户 Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-09
 */
@Service
public class ClientUserServiceImpl implements IClientUserService 
{
    @Autowired
    private ClientUserMapper clientUserMapper;

    /**
     * 查询客户端用户 
     * 
     * @param id 客户端用户 主键
     * @return 客户端用户 
     */
    @Override
    public ClientUser selectClientUserById(String id)
    {
        return clientUserMapper.selectClientUserById(id);
    }

    /**
     * 查询客户端用户 列表
     * 
     * @param clientUser 客户端用户 
     * @return 客户端用户 
     */
    @Override
    public List<ClientUser> selectClientUserList(ClientUser clientUser)
    {
        return clientUserMapper.selectClientUserList(clientUser);
    }

    /**
     * 新增客户端用户 
     * 
     * @param clientUser 客户端用户 
     * @return 结果
     */
    @Override
    public int insertClientUser(ClientUser clientUser)
    {
        return clientUserMapper.insertClientUser(clientUser);
    }

    /**
     * 修改客户端用户 
     * 
     * @param clientUser 客户端用户 
     * @return 结果
     */
    @Override
    public int updateClientUser(ClientUser clientUser)
    {
        return clientUserMapper.updateClientUser(clientUser);
    }

    /**
     * 批量删除客户端用户 
     * 
     * @param ids 需要删除的客户端用户 主键
     * @return 结果
     */
    @Override
    public int deleteClientUserByIds(String[] ids)
    {
        return clientUserMapper.deleteClientUserByIds(ids);
    }

    /**
     * 删除客户端用户 信息
     * 
     * @param id 客户端用户 主键
     * @return 结果
     */
    @Override
    public int deleteClientUserById(String id)
    {
        return clientUserMapper.deleteClientUserById(id);
    }
}
