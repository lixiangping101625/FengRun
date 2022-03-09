package com.ruoyi.system.service;

import com.ruoyi.system.domain.ClientUser;

import java.util.List;

/**
 * 客户端用户 Service接口
 * 
 * @author ruoyi
 * @date 2022-03-09
 */
public interface IClientUserService 
{
    /**
     * 查询客户端用户 
     * 
     * @param id 客户端用户 主键
     * @return 客户端用户 
     */
    public ClientUser selectClientUserById(String id);

    /**
     * 查询客户端用户 列表
     * 
     * @param clientUser 客户端用户 
     * @return 客户端用户 集合
     */
    public List<ClientUser> selectClientUserList(ClientUser clientUser);

    /**
     * 新增客户端用户 
     * 
     * @param clientUser 客户端用户 
     * @return 结果
     */
    public int insertClientUser(ClientUser clientUser);

    /**
     * 修改客户端用户 
     * 
     * @param clientUser 客户端用户 
     * @return 结果
     */
    public int updateClientUser(ClientUser clientUser);

    /**
     * 批量删除客户端用户 
     * 
     * @param ids 需要删除的客户端用户 主键集合
     * @return 结果
     */
    public int deleteClientUserByIds(String[] ids);

    /**
     * 删除客户端用户 信息
     * 
     * @param id 客户端用户 主键
     * @return 结果
     */
    public int deleteClientUserById(String id);
}
