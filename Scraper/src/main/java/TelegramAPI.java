import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TelegramAPI {
    private static final String API_URL = "https://api.telegram.org/bot";
    private static final String BOT_TOKEN = "YOUR_BOT_TOKEN";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + BOT_TOKEN + "/getMe")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}