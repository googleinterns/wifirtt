package structs;

public class UsageState {
    // Parameters
    private boolean retransmissionAllowed;
    private boolean retentionExpires;
    private int expireTime;
    private boolean staLocationPolicy;

    public void setRetransmissionAllowed(boolean retransmissionAllowed) {
        this.retransmissionAllowed = retransmissionAllowed;
    }

    public void setRetentionExpires(boolean retentionExpires) {
        this.retentionExpires = retentionExpires;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public void setStaLocationPolicy(boolean staLocationPolicy) {
        this.staLocationPolicy = staLocationPolicy;
    }
}
