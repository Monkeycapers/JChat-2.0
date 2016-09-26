/**
 * Created by Evan on 9/23/2016.
 */
public class LobbyCreateCommand extends Command {
    public LobbyCreateCommand () {
        name = "/createlobby";
        help = "Usage: /createlobby lobbyname";
    }
    @Override
    public String parse(String message) {
        try {
            String[] split = message.split(" ");
            return "," + split[1];
        }
        catch (Exception e) {
            return "invalid";
        }

    }
}
