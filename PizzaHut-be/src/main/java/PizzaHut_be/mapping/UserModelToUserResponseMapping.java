package PizzaHut_be.mapping;

import PizzaHut_be.mapping.converter.PathImageToUrlImageConverter;
import PizzaHut_be.model.dto.response.ResponseUser;
import PizzaHut_be.model.entity.UserModel;
import org.modelmapper.PropertyMap;

public class UserModelToUserResponseMapping extends PropertyMap<UserModel, ResponseUser> {
    private String minioUrl;

    public UserModelToUserResponseMapping(String minioUrl) {
        this.minioUrl = minioUrl;
    }

    @Override
    protected void configure() {
        using(new PathImageToUrlImageConverter(minioUrl)).map(source.getAvatar()).setAvatar(null);
    }
}
