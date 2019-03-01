package test.pojo.common;

import lombok.Getter;
import lombok.Setter;

/**
 * spring security json登录实体bean
 */
@Getter
@Setter
public class AuthenticationBean {
    private String username;
    private String password;
}
