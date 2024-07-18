package PizzaHut_be.mapping;

import PizzaHut_be.mapping.converter.StringToLocalDateConverter;
import PizzaHut_be.model.dto.GoogleUserDto;
import PizzaHut_be.model.entity.UserModel;
import PizzaHut_be.util.DateUtil;
import org.modelmapper.PropertyMap;

public class GoogleUserDtoToUserModelMapping extends PropertyMap<GoogleUserDto, UserModel> {
    @Override
    protected void configure() {
        using(new StringToLocalDateConverter(DateUtil.GOOGLE_DATE_PATTERN)).map(source.getBirthday()).setBirthday(null);
        map().setFirstName(source.getGivenName());
        map().setLastName(source.getFamilyName());
        map().setEmail(source.getEmail());
        map().setPhone(source.getPhoneNumber());
        map().setLocate(source.getLocale());
        map().setAvatar(source.getPicture());
    }
}
