package com.example.shoppringback.service;


import com.example.shoppringback.dto.KakaoProfile;
import com.example.shoppringback.dto.NaverProfile;
import com.example.shoppringback.entity.User;
import com.example.shoppringback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

    @Autowired
    private UserRepository userRepository;

    public User saveOrGetKakaoUser(KakaoProfile kakaoProfile) {
        String provider = "kakao";
        String providerId = String.valueOf(kakaoProfile.getId());

        return userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    newUser.setEmail(kakaoProfile.getKakaoAccount().getEmail());
                    newUser.setNickname(kakaoProfile.getProperties().getNickname());
                    newUser.setProfileImage(kakaoProfile.getProperties().getProfileImage());

                    // 표준화된 저장 형식
                    newUser.setPhoneNumber(formatPhoneNumber(kakaoProfile.getKakaoAccount().getPhoneNumber()));
                    newUser.setGender(mapGender(kakaoProfile.getKakaoAccount().getGender())); // male, female
                    newUser.setAgeRange(kakaoProfile.getKakaoAccount().getAgeRange());
                    newUser.setBirthyear(kakaoProfile.getKakaoAccount().getBirthyear());
                    newUser.setBirthday(formatBirthday(kakaoProfile.getKakaoAccount().getBirthday()));
                    newUser.setBirthdayType(kakaoProfile.getKakaoAccount().getBirthdayType());
                    newUser.setLeapMonth(kakaoProfile.getKakaoAccount().isLeapMonth());

                    return userRepository.save(newUser);
                });
    }



    public User saveOrGetNaverUser(NaverProfile naverProfile) {
        String provider = "naver";
        NaverProfile.Response res = naverProfile.getResponse();
        String providerId = res.getId();

        return userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    newUser.setEmail(res.getEmail());
                    newUser.setNickname(res.getNickname());
                    newUser.setProfileImage(res.getProfileImage());

                    // 통합 형식 변환
                    newUser.setPhoneNumber(formatPhoneNumber(res.getMobile()));
                    newUser.setGender(mapGender(res.getGender()));
                    newUser.setAgeRange(res.getAge());
                    newUser.setBirthyear(res.getBirthyear());
                    newUser.setBirthday(res.getBirthday().replace("-", "")); // "06-10" -> "0610"
                    newUser.setBirthdayType("SOLAR"); // 네이버는 생일타입 없음 → 기본값
                    newUser.setLeapMonth(false); // 네이버는 윤달 정보 없음 → false

                    return userRepository.save(newUser);
                });
    }

    private String mapGender(String gender) {
        if (gender == null) return null;
        return switch (gender.toLowerCase()) {
            case "m", "male" -> "male";
            case "f", "female" -> "female";
            default -> gender.toLowerCase(); // fallback
        };
    }

    private String formatPhoneNumber(String phone) {
        if (phone == null) return null;
        String onlyDigits = phone.replaceAll("[^0-9]", "");
        if (onlyDigits.startsWith("82")) {
            onlyDigits = "0" + onlyDigits.substring(2);
        }
        return onlyDigits.replaceFirst("^(\\d{3})(\\d{3,4})(\\d{4})$", "$1-$2-$3");
    }

    private String formatBirthday(String birthday) {
        if (birthday == null) return null;
        return birthday.replaceAll("-", ""); // "06-10" → "0610"
    }
}
