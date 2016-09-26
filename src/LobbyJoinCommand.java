/**
 * Created by Evan on 9/23/2016.
 */
public class LobbyJoinCommand extends Command {
    public LobbyJoinCommand () {
        name = "/joinlobby";
        help = "Usage: /joinlobby lobby";
    }
    @Override
    public String parse(String message) {
        try {
            String[] split = message.split(" ");
            return "," + split[1] + ",";
        }
        catch (Exception e) {
            return "invalid";
        }

    }
}
