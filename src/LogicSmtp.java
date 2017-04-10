import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogicSmtp {
	String mailExhangeServer;
	short port;

	public LogicSmtp(String mailExchangeServer, short port) {
		this.mailExhangeServer = mailExchangeServer;
		this.port = port;
	}

	public void sendMail(Message message) throws UnknownHostException,
			IOException, MyException {

		String rcptHost = defineHost(message.getTo());
		Socket socket;

		socket = new Socket(mailExhangeServer, 25);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out.write("HELO " + rcptHost + " \r\n");
		out.flush();
		String heloResponse = in.readLine();
		if (!heloResponse.startsWith("220")) {
			throw new MyException("Error while talking with RCPT Host");
		}
		out.write("MAIL from: <" + message.getFrom() + "> \r\n");
		out.flush();
		String fromResponse = in.readLine();
		if (!fromResponse.startsWith("250")) {
			throw new MyException("Error with from mail");
		}
//		System.out.println(heloResponse);
//		System.out.println(fromResponse);
		out.flush();
		out.write("RCPT to: <" + message.getTo() + "> \r\n");
		out.flush();
		String toResponse = in.readLine();
		if (!toResponse.startsWith("250")) {
			throw new MyException("Error with RCPT mail");
		}
//		System.out.println(toResponse);
		out.write("DATA \r\n");
		out.flush();
		String dataResponse = in.readLine();
		if (!dataResponse.startsWith("250")) {
			throw new MyException("Error");
		}
		String unknown = in.readLine();
		/*if (!dataResponse.startsWith("354")) {
			throw new MyException("Error with Data");
		}*/
		out.flush();
		out.write("from: " + message.getFrom() + "\r\n");
		out.flush();
		out.write("to: " + message.getTo() + "\r\n");
		out.flush();
		out.write("Subject: " + message.getSubject() + "\r\n");
		out.flush();
		out.write(message.getBody());
		out.flush();
		out.write("\r\n.\r\n");
		out.flush();
		String bodyResponse = in.readLine();
		if (!bodyResponse.startsWith("250")) {
			throw new MyException("Error with body");
		}
//		System.out.println(dataResponse);
//		System.out.println(bodyResponse);
	}

	private String defineHost(String to) {
		String s1 = to.split("@")[1];
		String s2 = s1.split("\\.")[0];
		String s3 = s2.toLowerCase();
		if (s3.equals("hotmail") || s3.equals("live")) {
			return "mx3.hotmail.com";
		} else if (s3.equals("yahoo")) {
			return "mta7.am0.yahoodns.net";
		} else if (s3.equals("gmail")) {
			return "alt3.gmail-smtp-in.l.google.com";
		} else {
			return "mgw02.tarassul.sy";
		}

	}
}
