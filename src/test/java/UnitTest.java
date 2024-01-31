import com.kinnarastudio.zenzivanet.model.ApiAccount;
import com.kinnarastudio.zenzivanet.model.ZenzivaClient;
import com.kinnarastudio.zenzivanet.model.exception.RequestException;
import com.kinnarastudio.zenzivanet.model.exception.ResponseException;
import com.kinnarastudio.zenzivanet.model.response.BalanceResponse;
import org.junit.Test;

public class UnitTest {
    public final static String USER_KEY = "Secret";
    public final static String API_KEY = "Secret";
    @Test
    public void getBalance() {
        final ZenzivaClient client = new ZenzivaClient.Builder()
                .setAccount(new ApiAccount(USER_KEY, API_KEY))
                .build();

        try {
            final BalanceResponse response = client.executeGetBalanceCheck();
            System.out.println("Balance : " + response.getBalance());
            System.out.println("Status : " + response.getStatus());
            System.out.println("Text : " + response.getText());
        } catch (RequestException | ResponseException e) {
            throw new RuntimeException(e);
        }
    }
}
