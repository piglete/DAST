package club.map.base.manager;

import club.map.base.model.AuthToken;
import club.map.base.model.AuthType;
import club.map.core.manager.GenericManager;

public interface AuthTokenManager extends GenericManager<AuthToken, Integer> {
    String login(AuthType type, Integer foreignerId);

    void logoutByRemove(String authorization);

    AuthToken getByToken(String authorization);
}