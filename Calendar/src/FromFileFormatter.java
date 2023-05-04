
import java.io.IOException;
import java.util.TreeSet;

public interface FromFileFormatter {
	public TreeSet<Event> formatEvents() throws IOException;
}
