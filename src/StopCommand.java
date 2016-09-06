/**
 * Created by Evan on 8/31/2016.
 */
public class StopCommand extends Command {

    public StopCommand () {
        name = "/stop";
    }
    @Override
    public String parse(String message) {
        return "";
    }
}
