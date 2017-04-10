import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	private Pattern pattern;
	private Matcher matcher;
	private String from;
	private String to;
	private String subject;
	private String body;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public Message(String from, String to, String subject, String body)
			throws MyException {
		pattern = Pattern.compile(EMAIL_PATTERN);
		if (!this.setFrom(from))
			throw new MyException("Error in From email expression");
		if (!this.setTo(to))
			throw new MyException(
					"Error in To email expression or invalid host");
		this.setSubject(subject);
		this.setBody(body);
	}

	public String getFrom() {
		return from;
	}

	public boolean setFrom(String from) {
		matcher = pattern.matcher(from);
		this.from=from;
		return matcher.matches();
	}

	public String getTo() {
		return to;
	}

	public boolean setTo(String to) {
		matcher = pattern.matcher(to);
		this.to=to;
		return matcher.matches();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
