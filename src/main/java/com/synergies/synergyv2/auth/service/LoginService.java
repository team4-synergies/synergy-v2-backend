package com.synergies.synergyv2.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergies.synergyv2.model.dto.KakaoTokenInfoDto;
import com.synergies.synergyv2.model.dto.KakaoUserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LoginService {

    @Value("${kakao.config.oauth.client_id}")
    private String kakaoOauthClientId;

    @Value("${kakao.config.oauth.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.config.oauth.client_secret}")
    private String kakaoOauthSecret;

    @Value("${kakao.config.oauth.logout_uri}")
    private String kakaoLogoutUri;

    public String getAuthorizationUrl() {
        return "https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + kakaoOauthClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code";
    }

    public KakaoTokenInfoDto getToken(String authorizeCode) throws IOException {
        final String RequestUrl = "https://kauth.kakao.com/oauth/token";
        final List<NameValuePair> postParams = new ArrayList<>();
        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", kakaoOauthClientId)); // REST API KEY
        postParams.add(new BasicNameValuePair("redirect_uri", kakaoRedirectUri)); // 리다이렉트 URI
        postParams.add(new BasicNameValuePair("client_secret", kakaoOauthSecret));
        postParams.add(new BasicNameValuePair("code", authorizeCode)); // 로그인 과정 중 얻은 code 값

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(RequestUrl);
        post.setEntity(new UrlEncodedFormEntity(postParams));
        HttpResponse response = client.execute(post);

        // JSON 형태 반환값 처리
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getEntity().getContent());
        return mapper.treeToValue(jsonNode, KakaoTokenInfoDto.class);
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws IOException {
        final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);

        // add header
        post.addHeader("Authorization", "Bearer " + accessToken);

        JsonNode returnNode;

        HttpResponse response = client.execute(post);

        // JSON 형태 반환값 처리
        ObjectMapper mapper = new ObjectMapper();
        returnNode = mapper.readValue(response.getEntity().getContent(), JsonNode.class);
        log.info("카카오 로그인 정보 : "+ returnNode);
        return KakaoUserInfoDto.builder()
                .userKakaoId(String.valueOf(returnNode.get("id")))
                .userNickname(returnNode.get("kakao_account").get("profile").has("nickname") ? returnNode.get("kakao_account").get("profile").get("nickname").asText() : "")
                .email(returnNode.get("kakao_account").has("email") ? returnNode.get("kakao_account").get("email").asText() : "")
                .profileImage(returnNode.get("kakao_account").get("profile").has("profile_image_url") ? returnNode.get("kakao_account").get("profile").get("profile_image_url").asText() : "")
                .build();
    }

    public void expiredKakaoAccessToken(String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost expiredPost = new HttpPost(kakaoLogoutUri);
        expiredPost.setHeader("Authorization", "Bearer "  + accessToken);
        client.execute(expiredPost);
    }
}
