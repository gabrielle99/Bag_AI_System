package com.tianci.utils.jwt;

import com.tianci.model.user.pojo.TcUserObject;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AppJwtUtil {
    private static final int TOKEN_TIME_OUT = 3600;
    // SECRET KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // Minimum refresh interval (s)
    private static final int REFRESH_TIME = 3600;

    /**
     * Get token
     *
     * @param user client user
     * @return
     */
    public static String getToken(TcUserObject user) {
        return getToken(user.getId());
    }


    // Produce ID
    private static String getToken(Long id){
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id",id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime))  //issue date
                .setSubject("system")  //description
                .setIssuer("tianci") //issuer
                .setAudience("app")  //receiver
                .compressWith(CompressionCodecs.GZIP)  //data compression method
                .signWith(SignatureAlgorithm.HS512, generalKey()) //encrypt method
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //expire time stamp -- * 1000 since ms
                .addClaims(claimMaps) //claim info
                .compact();
    }

    /**
     * get token's claims info
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * get payload body info
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * get hearder body info
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * if expire
     *
     * @param claims
     * @return -1: invalid，0：effective，1：expired，2：wrong token
     */
    public static int verifyToken(Claims claims) {
        if(claims==null){
            return 1;
        }
        try {
            claims.getExpiration()
                    .before(new Date());
            // need to refresh TOKEN automatically
            if((claims.getExpiration().getTime()-System.currentTimeMillis())>REFRESH_TIME*1000){
                return -1;
            }else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
    }

    /**
     * generate secret key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
//        TcUserObject user = new TcUserObject();
//        user.setId(2L);
//        String token = getToken(user);
//        System.out.println("token:"+token);

//        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWMQQoDIQxF75L1CFHT0cxtUnWRQougAy2ld5-46O4_Hu9_4TEVDhDyQkHQYavRUanFMfLNpcg-7ZUxxAQbqEw4_E4550iBNxjn3erxGbM9lx_DcKq8ihrKWdd377bbu__ThCtVc_53AUD0tvyBAAAA.4-9YQQQMeZ0E8cXBcjdybagqksUvfqWhVdOJJbYTLtmtfcNneFZuZUGLgoNFVEzz5ygHfa2t1Sn8XxrvEwSkvg";
//        String token="eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAEWLQQrDMAzA_uJzc4jTpE5_47opuLARcAobY3-vd9pNQugD51BYgXCRtgsHjBnDnAuFrdQU0iEHZ5QkrcIEygPWWGYiShXjBHZtftvbRnv8upnrUH6KuvK1u3Lvzu3V_yv5qt7i9wa_q5-JgQAAAA.JXZHWATkIJ7BKU5jGuKrczErKUCEdlJJn3W18xgbsSRdpuvGEEzepVl0JfYg2GI6qYBCe57NwVZ6czt3wY-jPw";

//        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWL0QqDMAwA_yXPFlYbTePftFohw0khLWzI_n3xYW93HHfBswksQHHPW_TBMXFxWMLo8jyymyhj4BV3pAcMIKnB4mdkCjhFHkB7tls_2srr7qqmTdK5imnqm2mq1bi863-N_l7F2tmP4_sDSiRE1IQAAAA.-yeapzm4zkHhZlg3xLC5EeWZYArrSNOd3pY50SvZZrnnf3UfVf5Ii8Bw5ke6-vf0Uv3V3bOoAMAFtx0NB858pg";
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWL0QqDMAwA_yXPFlYbTePftFohw0khLWzI_n3xYW93HHfBswksQHHPW_TBMXFxWMLo8jyymyhj4BV3pAcMIKnB4mdkCjhFHkB7tls_2srr7qqmTdK5imnqm2mq1bi863-N_l7F2tmP4_sDSiRE1IQAAAA.-yeapzm4zkHhZlg3xLC5EeWZYArrSNOd3pY50SvZZrnnf3UfVf5Ii8Bw5ke6-vf0Uv3V3bOoAMAFtx0NB858pg";
        Claims claims = AppJwtUtil.getClaimsBody(token);
        Object id = claims.get("id");
        System.out.println("id:"+id);
    }
}
