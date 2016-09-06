/**
 * Created by Evan on 9/2/2016.
 */
public class SignInCommand extends Command {

    public SignInCommand () {
        name = "/signin";
        help = "Usage: /signin user password";
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