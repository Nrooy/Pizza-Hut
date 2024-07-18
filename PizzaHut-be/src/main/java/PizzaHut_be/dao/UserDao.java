package PizzaHut_be.dao;

import PizzaHut_be.dao.repository.UserModelRepository;
import PizzaHut_be.model.entity.UserModel;
import lombok.CustomLog;
import org.springframework.stereotype.Component;

@CustomLog
@Component
public class UserDao extends BaseDao<UserModel, String>{

    private final UserModelRepository userModelRepository;

    public UserDao(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public UserModel findOneById(String s) {
        return null;
    }

    @Override
    public UserModel save(UserModel entity) {
        return null;
    }
    public UserModel update(UserModel entity) {
        UserModel updatedUser;
        try {
            updatedUser = userModelRepository.save(entity);
        } catch (Exception e) {
            log.error("Update user failed: ", e);
            throw e;
        }
        return updatedUser;
    }
    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(UserModel entity) {

    }
}
