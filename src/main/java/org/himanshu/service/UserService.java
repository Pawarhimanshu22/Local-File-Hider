package org.himanshu.service;

import org.himanshu.dao.UserDAO;
import org.himanshu.model.User;

public class UserService {

    public static Integer saveUser(User user)
    {
        try
        {
            if (UserDAO.isUserExist(user.getEmail()))
            {
                return 0;
            }
            else
            {
                UserDAO.saveUser(user);
                return 1;
            }

        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}
