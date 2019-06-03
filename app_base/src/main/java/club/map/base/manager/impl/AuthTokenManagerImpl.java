package club.map.base.manager.impl;

import club.map.base.manager.AuthTokenManager;
import club.map.base.mapper.AuthTokenMapper;
import club.map.base.model.AuthToken;
import club.map.base.model.AuthType;
import club.map.core.manager.impl.GenericManagerImpl;
import club.map.core.model.DAOFilter;
import club.map.core.model.Operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenManagerImpl extends GenericManagerImpl<AuthToken, Integer> implements AuthTokenManager {

    @Autowired
    public AuthTokenManagerImpl(AuthTokenMapper mapper) {
        super(mapper, AuthToken.class);
    }

    @Override
    public String login(AuthType type, Integer foreignId) {
        String token = generateToken();

        AuthToken at = new AuthToken();
        at.setForeignId(foreignId);
        at.setType(type.getCode());
        at.setToken(token);
        super.save(at);
        return token;
    }

    @Override
    public void logoutByRemove(String authorization) {
        DAOFilter filter = new DAOFilter(AuthToken.class);
        filter.addSearch(authorization, Operate.EQUAL, "token");
        AuthToken authToken = super.get(filter);
        if (authToken != null) {
            super.remove(authToken.getId());
        }
    }

    @Override
    public AuthToken getByToken(String authorization) {
        return super.get("token", authorization);
    }

    private String generateToken() {
        return "Bearer " + UUID.randomUUID().toString();
    }

}