package com.project.shop.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.shop.entity.RefreshToken;

@Service
public interface RefreshTokenService {
 
	public Optional<RefreshToken> findByToken(String token);
	
	public RefreshToken createRefreshToken(Long userId) ;
	
	public RefreshToken verifyExpiration(RefreshToken token) ;
	
	
	  public int deleteByUserId(Long userId);
}
