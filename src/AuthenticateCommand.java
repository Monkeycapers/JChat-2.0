/**
 * Created by Evan on 8/30/2016.
 */
public class AuthenticateCommand extends Command {

    public AuthenticateCommand () {
        name = "/auth";
    }
    @Override
    public String parse(String message) {
        String [] split = message.split(" ");
        //todo: try to encypt password here
        return "," + split[1] + "," + split[2];
    }
}
