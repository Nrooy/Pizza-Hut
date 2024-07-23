package PizzaHut_be.service;

import PizzaHut_be.dao.UserDao;
import PizzaHut_be.dao.repository.UserModelRepository;
import PizzaHut_be.model.builder.ResponseBuilder;
import PizzaHut_be.model.dto.ResponseDto;
import PizzaHut_be.model.dto.request.LoginOtpRequest;
import PizzaHut_be.model.dto.request.LoginRequest;
import PizzaHut_be.model.dto.response.LoginOtpResponse;
import PizzaHut_be.model.dto.response.LoginResponse;
import PizzaHut_be.model.dto.response.ResponseUser;
import PizzaHut_be.model.dto.response.UserResponse;
import PizzaHut_be.model.entity.UserModel;
import PizzaHut_be.model.enums.OtpDestinationEnum;
import PizzaHut_be.model.enums.OtpRequestTypeEnum;
import PizzaHut_be.model.enums.StatusCodeEnum;
import PizzaHut_be.model.mapper.CommonMapper;
import PizzaHut_be.config.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final OtpService otpService;
    private final LanguageService languageService;
    private final UserModelRepository repository;
    private final UserDao userDao;
    private final JwtService jwtService;
    private final CommonMapper mapper;

    public UserModel getUserInfoFromContext() {
        try {
            UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            log.info("User details by authentication: " + userDetails);

            return userDetails.getUserModel();
        } catch (Exception e) {
            log.error("Get user from context failed by: ", e.getMessage());

            return null;
        }
    }    /***
     * Check if username is valid, and send OTP
     * @param loginOtpRequest
     * @return
     */
    public ResponseEntity<ResponseDto<LoginOtpResponse>> requestLogin(LoginOtpRequest loginOtpRequest) {
        String username = loginOtpRequest.getUsername();
        LoginOtpResponse loginOtpResponse = new LoginOtpResponse();
        loginOtpResponse.setHasSentOtp(true);

        return getCheckResponseByEmail(username, loginOtpResponse);
    }

    private ResponseEntity<ResponseDto<LoginOtpResponse>> getCheckResponseByEmail(String username,
                                                                                  LoginOtpResponse loginOtpResponse) {

        boolean isSentOtp = otpService.sendOtp(
                null,
                null,
                LocaleContextHolder.getLocale(),
                username,
                OtpDestinationEnum.EMAIL,
                OtpRequestTypeEnum.LOGIN
        );

        if (isSentOtp) {
            return ResponseBuilder.okResponse(
                    languageService.getMessage("otp.sent.successfully"),
                    loginOtpResponse,
                    StatusCodeEnum.OTP1001);
        }

        return ResponseBuilder.badRequestResponse(
                languageService.getMessage("otp.sent.failed"),
                StatusCodeEnum.OTP0001);
    }

    /***
     * Login by username/email/password
     * @param loginRequest
     * @return
     */
    public ResponseEntity<ResponseDto<LoginResponse>> login(LoginRequest loginRequest) {
        UserResponse userResponse = getLoginUserResponse(loginRequest);

        if (userResponse.getUserModel() == null) {
            return userResponse.getResponse();
        } else {
            UserModel userModel = userResponse.getUserModel();
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(jwtService.generateJwtTokenByUserId(userModel.getId()));
            loginResponse.setUserInfo(mapper.map(userModel, ResponseUser.class));

            return ResponseBuilder.okResponse(
                    languageService.getMessage("login.main.success"),
                    loginResponse,
                    StatusCodeEnum.LOGIN1204);
        }
    }

    public UserResponse getLoginUserResponse(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String passwordOrOtp = loginRequest.getPassword();
        log.info(loginRequest.getUsername());
        UserModel userModel = userDao.findOneUserModel(username);
        log.info(userModel.getEmail(), userModel);

        if (passwordOrOtp != null) {
            UserResponse otpLoginResponse = handleOtpLogin(userModel, username, passwordOrOtp);
            if (otpLoginResponse != null) {
                return otpLoginResponse;
            }
        }
        return new UserResponse(null, ResponseBuilder.badRequestResponse(
                languageService.getMessage("login.password.or.otp.invalid"),
                StatusCodeEnum.LOGIN0209), false);
    }

    private UserResponse handleOtpLogin(UserModel userModel, String username, String passwordOrOtp) {
        if (otpService.verifyOtp(username, OtpRequestTypeEnum.LOGIN, passwordOrOtp)) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserModel(userModel);
            return userResponse;
        }
        return null;
    }
}
