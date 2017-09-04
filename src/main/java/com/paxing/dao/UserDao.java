package com.paxing.dao;

import com.paxing.annotation.MyBatisDao;
import com.paxing.base.CrudDao;
import com.paxing.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wayne-zhang
 * @date 2017/7/26 20:47.
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 获取所有用户列表
     * @return
     */
    List<User> listAllUser();

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User getUserById(@Param("id") Long id);

    /**
     * 更新用户的信息
     * @param user
     */
    long updateUser(User user);

    /**
     * 根据id删除指定的用户
     * @param id
     */
    void deleteUserById(@Param("id") Long id);

    /**
     * 批量添加用户
     * @param list
     */
    void batchSaveUser(List<User> list);

    /**
     * 批量删除用户
     * @param ids
     */
    void batchDeleteUser(String[] ids);
}
