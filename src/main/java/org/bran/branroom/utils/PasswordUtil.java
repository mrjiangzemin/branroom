package org.bran.branroom.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.bran.branroom.entity.User;
import org.springframework.stereotype.Component;
import sun.security.util.Password;

@Component
public class PasswordUtil {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final String ALGORITH_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    /**
     * 给用户加盐并且密码加密
     *
     * @param user
     * @return
     */
    public User encryptPassword(User user) {
        //获取salt
        String salt = randomNumberGenerator.nextBytes().toHex();
        String newPassword = new SimpleHash(ALGORITH_NAME, user.getPassword(), ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
        user.setSalt(salt);
        user.setPassword(newPassword);
        return user;
    }

    /**
     * 密码验证
     *
     * @param user
     * @param Pwd
     * @return
     */
    public boolean Match(User user, String Pwd) {
        String salt = user.getSalt();
        String encryptedPwd = new SimpleHash(ALGORITH_NAME, Pwd, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
        if (encryptedPwd.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
