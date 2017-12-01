package equifax.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import equifax.bin.es.response.query.IdentityQueryObj;
import equifax.bin.es.response.response.Identity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class UserTest {

    private Response response;
    private IdentityQueryObj identityQueryObj;

    @Test
    public void check_for_valid_firstName() throws IOException {

        RestAssured.baseURI  = "http://localhost:8080/identities";
        String queryJsonInString = "{\"requestType\":\"test\",\"consumerIdentity\":{\"names\":[{\"identifier\":\"CURRENT\",\"prefix\":\"Mr.\"," +
                "\"firstName\":\"Kisung\"," +
                "\"lastName\":\"Cathrine\",\"middleName\":\"M\",\"suffix\":\"Jr.\"}],\"ssn\":\"152261\",\"addresses\":[{\"addressLine1\":\"line two MOxciEOkmBbvqpaMdrxqALxJWPmXGc\"}]}}";

        try {
            identityQueryObj = new ObjectMapper().readValue(queryJsonInString, IdentityQueryObj.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = given()
                .header("Content-Type", "application/json")
                .body(queryJsonInString)
                .when().
                        post();

        List<Identity> identitiesResponse = Arrays.asList(response.getBody().as(Identity[].class));
        Assert.assertTrue(identitiesResponse.size()>0, "Response body is empty");
        for (Identity identityResponse : identitiesResponse){
            Assert.assertEquals(identityQueryObj.getConsumerIdentity().getSsn(),
                    identityResponse.getSsn());
            for (int i=0; i<identityResponse.getNames().size(); i++){
                Assert.assertEquals(identityQueryObj.getConsumerIdentity().getNames().get(i).getFirstName(),
                        identityResponse.getNames().get(i).getFirstName());
            }
        }
    }
    @Test
    public void check_for_invalid_firstName() throws IOException {

        RestAssured.baseURI  = "http://localhost:8080/identities";
        String queryJsonInString = "{\"requestType\":\"test\",\"consumerIdentity\":{\"names\":[{\"identifier\":\"CURRENT\",\"prefix\":\"Mr.\"," +
                "\"firstName\":\"Mike\"," +
                "\"lastName\":\"Cathrine\",\"middleName\":\"M\",\"suffix\":\"Jr.\"}],\"ssn\":\"152261\",\"addresses\":[{\"addressLine1\":\"line two MOxciEOkmBbvqpaMdrxqALxJWPmXGc\"}]}}";

        try {
            identityQueryObj = new ObjectMapper().readValue(queryJsonInString, IdentityQueryObj.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = given()
                .header("Content-Type", "application/json")
                .body(queryJsonInString)
                .when().
                        post();

        List<Identity> identitiesResponse = Arrays.asList(response.getBody().as(Identity[].class));

        Assert.assertTrue(identitiesResponse.size()==0);

    }
}
