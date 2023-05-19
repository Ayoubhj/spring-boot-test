package com.project.shop.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.shop.entity.RefreshToken;
import com.project.shop.exception.TokenRefreshException;
import com.project.shop.repo.RefreshTokenRepository;
import com.project.shop.repo.UserRepository;
import com.project.shop.services.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

	 @Value("${jwtRefreshExpirationMs}")
	  private Long refreshTokenDurationMs;

	  @Autowired
	  private RefreshTokenRepository refreshTokenRepository;

	  @Autowired
	  private UserRepository userRepository;
	  
	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {
		 RefreshToken refreshToken = new RefreshToken();

		    refreshToken.setUser(userRepository.findById(userId).get());
		    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		    refreshToken.setToken(UUID.randomUUID().toString());

		    refreshToken = refreshTokenRepository.save(refreshToken);
		    return refreshToken;
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		 if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
		      refreshTokenRepository.delete(token);
		      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
		    }

		    return token;
	}

	@Override
	public int deleteByUserId(Long userId) {
		 return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}

}
