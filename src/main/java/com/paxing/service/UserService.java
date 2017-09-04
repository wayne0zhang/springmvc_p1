package com.paxing.service;

import com.paxing.base.CrudService;
import com.paxing.dao.UserDao;
import com.paxing.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wayne-zhang
 * @date 2017/7/26 20:49.
 */
@Service
//只能被应用到public方法上, 对于其它非public的方法,如果标记了@Transactional也不会报错,但方法没有事务功能.
@Transactional(readOnly = true)//是否为只读事务 true表示只读，false表示不是，发生错误会回滚
public class UserService extends CrudService<UserDao, User> {
    public User getUserById(long id) {
        return dao.getUserById(id);
    }

    public List<User> listAllUser() {
        return dao.listAllUser();
    }

    //对于不是添加，修改，删除，设置readOnly = false，
    @Transactional(readOnly = false)
    public void batchSaveUser(List<User> list) {
        dao.batchSaveUser(list);
    }

    @Transactional(readOnly = false)
    public long updateUser(User user) {
        return dao.updateUser(user);
    }
}
