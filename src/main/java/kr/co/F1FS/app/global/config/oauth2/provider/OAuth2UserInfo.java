package kr.co.F1FS.app.global.config.oauth2.provider;

// Provider에 따라 다른 형태의 유저 데이터를 추출
public interface OAuth2UserInfo {
    String getProviderId();
    String getName();
    String getEmail();
    String getProvider();
}
