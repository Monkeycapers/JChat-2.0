/**
 * Created by Evan on 9/3/2016.
 */
public class PromoteCommand extends Command {
    public PromoteCommand () {
        name = "/promote";
        help = "Usage: /promote user rank";
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
