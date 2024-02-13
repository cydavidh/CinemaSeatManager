package cydavidh.cinemaseatmanager.dto;

import java.util.UUID;

public class TokenRequest {
    private UUID token;

    public TokenRequest() {
    }

    public TokenRequest(UUID uuid) {
        this.token = uuid;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
