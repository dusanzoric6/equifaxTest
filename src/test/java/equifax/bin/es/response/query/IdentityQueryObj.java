
package equifax.bin.es.response.query;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentityQueryObj {

    @SerializedName("requestType")
    @Expose
    private String requestType;
    @SerializedName("consumerIdentity")
    @Expose
    private ConsumerIdentity consumerIdentity;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public ConsumerIdentity getConsumerIdentity() {
        return consumerIdentity;
    }

    public void setConsumerIdentity(ConsumerIdentity consumerIdentity) {
        this.consumerIdentity = consumerIdentity;
    }

}
