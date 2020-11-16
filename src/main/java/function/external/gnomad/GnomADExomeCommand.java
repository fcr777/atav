package function.external.gnomad;

/**
 *
 * @author nick
 */
public class GnomADExomeCommand extends GnomADCommand {
    private static GnomADExomeCommand single_instance = null;

    public static GnomADExomeCommand getInstance() {
        if (single_instance == null) {
            single_instance = new GnomADExomeCommand();
        }

        return single_instance;
    }
}
