import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class ChatSRV extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("ChatSRV has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatSRV has been disabled!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();

        // Send a POST request to your Flask API
        sendPostRequest(playerName, message);
    }

    public void sendPostRequest(String playerName, String message) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://your-api-url-here/chatsrv/post"); // Replace with your API endpoint URL

            // Create a JSON object with the player's username as the key and message as the value
            String requestBody = String.format("{\"%s\":\"%s\"}", playerName, message);

            // Set the request headers
            httpPost.setHeader(playerName, "application/json");

            // Set the request body
            httpPost.setEntity(new StringEntity(requestBody));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Handle the response if needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
