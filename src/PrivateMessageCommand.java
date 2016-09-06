/**
 * Created by Evan on 9/3/2016.
 */
public class PrivateMessageCommand extends Command {
    public PrivateMessageCommand () {
        name = "/pm";
        help = "Usage: /pm user message";
    }
    @Override
    public String parse(String message) {
        try {
            String[] split = message.split(" ");
            return "," + split[1] + "," + split[2];
        }
        catch (Exception e) {
            return "invalid";
        }

    }
}
