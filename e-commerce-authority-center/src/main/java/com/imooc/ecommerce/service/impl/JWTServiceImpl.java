package com.imooc.ecommerce.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.AuthorityConstant;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.mapper.EcommerceUserMapper;
import com.imooc.ecommerce.service.IJWTService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


/*JWT相关服务接口实现*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)/*任意异常回滚*/
public class JWTServiceImpl implements IJWTService {

    private final EcommerceUserMapper ecommerceUserMapper;

    public JWTServiceImpl(EcommerceUserMapper ecommerceUserMapper) {
        this.ecommerceUserMapper = ecommerceUserMapper;
    }


    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username,password,0);
    }

    @Override
    public String generateToken(String username, String password, int expire)
            throws Exception {


        //首先需要验证用户能够通过授权校验，即输入的用户名和密码能否匹配数据表记录
        EcommerceUser ecommerceUser =
                ecommerceUserMapper.findByUsernameAndPassword(username, password);
        if(null==ecommerceUser){
            log.error("can not find user:[{}],[{}]",username,password);
            return null;
        }

        //Token中塞入对象，即JWT中存储的信息，后端拿到这些信息就可以知道哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(
                ecommerceUser.getId(),ecommerceUser.getUsername()
        );
        if(expire<=0){
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        //计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                //jwt payload --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                //jwt id
                .setId(UUID.randomUUID().toString())
                //jwt 过期时间
                .setExpiration(expireDate)
                //jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword)
            throws Exception{

        //先去校验用户名是否存在，如果存在，不能重复注册
        EcommerceUser oldUser =
                ecommerceUserMapper.findByUsername(usernameAndPassword.getUsername());
        if(null!=oldUser){
            log.error("username is registered: [{}]",oldUser.getUsername());
            return null;
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        ecommerceUser.setPassword(usernameAndPassword.getPassword());//MD5 编码以后
        ecommerceUser.setExtraInfo("{}");

        //注册一个新用户，写一条记录到数据表中
        ecommerceUserMapper.save(ecommerceUser);
        EcommerceUser user = ecommerceUserMapper.findById(ecommerceUser.getId());
        log.info("register user success: [{}],[{}]",user.getUsername(),
                user.getId());

        //生成 token 并返回
        return generateToken(ecommerceUser.getUsername(),ecommerceUser.getPassword());
    }

    /*根据本地存储的私钥获取到PrivateKey对象*/
    private PrivateKey getPrivateKey() throws Exception{

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }
}
