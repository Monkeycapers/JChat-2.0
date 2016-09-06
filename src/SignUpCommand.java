/**
 * Created by Evan on 9/2/2016.
 */
public class SignUpCommand extends Command {
    public SignUpCommand () {
        name = "/signup";
        help = "Usage: /signup user password";
    }
    @Override
    public String parse(String message) {
        try {
            String[] split = message.split(" ");
            //for (int i = 0; i <= split.length - 1; i ++) {
            //System.out.println(i + ": " + split[i]);
            //}
            return "," + split[1] + "," + split[2];
        }
        catch (Exception e) {
            return "invalid";
        }

    }
}
