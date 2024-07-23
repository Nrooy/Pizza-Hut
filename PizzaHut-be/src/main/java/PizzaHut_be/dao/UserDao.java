package PizzaHut_be.dao;

import PizzaHut_be.dao.repository.UserModelRepository;
import PizzaHut_be.model.constant.RedisPrefixKeyConstant;
import PizzaHut_be.model.dto.UserModelQueryDto;
import PizzaHut_be.model.entity.UserModel;
import PizzaHut_be.model.enums.AccountStatusEnum;
import PizzaHut_be.model.mapper.CommonMapper;
import PizzaHut_be.util.Util;
import lombok.CustomLog;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@CustomLog
@Component
public class UserDao extends BaseDao<UserModel, String> {

    private final UserModelRepository userModelRepository;

    public UserDao(UserModelRepository userModelRepository, CommonMapper mapper) {
        this.userModelRepository = userModelRepository;
        this.mapper = mapper;
    }

    private final CommonMapper mapper;

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

    @SneakyThrows
    public UserModel findOneUserModel(@NonNull String query) {
        Jedis jedis = null;
        try {
            jedis = createConnection();
            String jsonString = jedis.get(Util.generateRedisKey(RedisPrefixKeyConstant.LOGIN, query));

            Optional<UserModel> userModel = userModelRepository.findOneByEmail(query);
            if (userModel.isPresent()) {
                new Thread(() -> {
                    saveUserInfoDataToRedis(userModel.get());
                }).start();
            }

            return  userModel.orElse(null);
        } catch (Exception e) {
            throw e;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @SneakyThrows
    public void saveUserInfoDataToRedis(UserModel userModel) {

        UserModelQueryDto userModelQueryDto = UserModelQueryDto
                .builder()
                .id(userModel.getId())
                .status(AccountStatusEnum.ACTIVE).build();

        Jedis jedis = null;

        try {
            jedis = createConnection();
            String userJson = mapper.writeValueAsString(userModel);
            String userQueryValueJson = mapper.writeValueAsString(userModelQueryDto);
            jedis.set(Util.generateRedisKey(RedisPrefixKeyConstant.USER, userModel.getId()), userJson);

            if (!Util.isNullOrEmpty(userModel.getEmail())) {
                jedis.set(Util.generateRedisKey(RedisPrefixKeyConstant.LOGIN, userModel.getEmail()), userQueryValueJson);
            }
        }catch (Exception e) {
            log.error("Save user basic info in redis failed: ", e);
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(UserModel entity) {

    }

}
