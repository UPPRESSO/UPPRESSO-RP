package sdk;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UPPRESSOToken {
    private List<String> audience;
    private String algorithm;
    private Map<String, Claim> claims;
    private String contentType;
    private Date expireAt;
    private String id;
    private Date issuedAt;
    private String issuer;
    private String keyID;
    private Date notBefore;
    private String subject;
    private String type;
    private boolean valid;

    public void init(DecodedJWT token, String sub){
        audience = token.getAudience();
        algorithm = token.getAlgorithm();
        claims = token.getClaims();
        contentType = token.getContentType();
        expireAt = token.getExpiresAt();
        id = token.getId();
        issuedAt = token.getIssuedAt();
        issuer = token.getIssuer();
        keyID = token.getKeyId();
        notBefore = token.getNotBefore();
        subject = sub;
        type = token.getType();
        
    }

    public List<String> getAudience() {
        return audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Map<String, Claim> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Claim> claims) {
        this.claims = claims;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean isvalid) {
        this.valid = isvalid;
    }

}
