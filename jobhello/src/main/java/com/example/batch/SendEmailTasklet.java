package com.example.batch;

import java.sql.*;
import java.util.Properties;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTasklet implements Tasklet {

	private String dbUrl = "jdbc:mysql://localhost:3308/jobmail";
	private String dbUsername = "root";
	private String dbPassword = "root";
	private JavaMailSender mailSender;
	private boolean emailsenduser = false;

	public SendEmailTasklet() {
	}

	public SendEmailTasklet(String dbUrl, String dbUsername, String dbPassword) {
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		if (!emailsenduser) {
			sendAllInactiveUserEmail();
			sendInactiveUserEmail();
			emailsenduser = true;
		} else {
			System.out.println("mail already send to all users");
		}
		return RepeatStatus.FINISHED;
	}

	public void sendInactiveUserEmail() throws SQLException, MessagingException {
		String host = "smtp.gmail.com";
		final String user = "logeshkumar1509@gmail.com";
		final String password = "cuds logk snfc yrcg";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(user, password);
			}
		});

		String query = "SELECT * FROM users WHERE Status = 'INACTIVE'";

		try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				String status = rs.getString("Status");

				if ("INACTIVE".equals(status)) {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					message.setSubject("Your Account is Inactive");

					String emailContent = "Dear " + name + ",<br><br>"
							+ "We would like to inform you that your account is currently inactive.<br>"
							+ "Please contact support to resolve the issue.<br><br>" + "Best Regards,<br>Support Team";

					message.setContent(emailContent, "text/html");

					Transport.send(message);
					System.out.println("Email sent to inactive user: " + name + " (" + email + ")");

				}
			}
			conn.close();
		} catch (SQLException | MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendAllInactiveUserEmail() throws SQLException, MessagingException {
		String host = "smtp.gmail.com";
		final String user = "logeshkumar1509@gmail.com";
		final String password = "cuds logk snfc yrcg";

		String to = "logeshkumarkcg@gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			String SelectQuery = "Select * from users";
			try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
					PreparedStatement pstmt = conn.prepareStatement(SelectQuery)) {
				ResultSet rs = pstmt.executeQuery();
				StringBuilder send = new StringBuilder();

				send.append("<!DOCTYPE html>\r\n").append("<html lang=\"en\">\r\n").append("<head>\r\n")
						.append("    <meta charset=\"UTF-8\">\r\n")
						.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n")
						.append("    <title>Simple Table</title>\r\n").append("    <style>\r\n")
						.append("        /* Table Styling */\r\n").append("        table {\r\n")
						.append("            width: 80%;\r\n").append("            border-collapse: collapse;\r\n")
						.append("            margin: 20px auto;\r\n")
						.append("            font-family: Arial, sans-serif;\r\n").append("        }\r\n")
						.append("        /* Table Header */\r\n").append("        th {\r\n")
						.append("            padding: 12px 15px;\r\n").append("            text-align: left;\r\n")
						.append("            font-weight: bold;\r\n").append("        }\r\n")
						.append("        /* Table Cells */\r\n").append("        td {\r\n")
						.append("            padding: 12px 15px;\r\n").append("            border: 1px solid #ddd;\r\n")
						.append("            text-align: left;\r\n").append("        }\r\n")
						.append("        /* Border Styling */\r\n").append("        td, th {\r\n")
						.append("            border: 1px solid #ddd;\r\n").append("        }\r\n")
						.append("        /* Responsive Design for Mobile */\r\n")
						.append("        @media screen and (max-width: 600px) {\r\n").append("            table {\r\n")
						.append("                width: 100%;\r\n").append("                font-size: 14px;\r\n")
						.append("            }\r\n").append("            td, th {\r\n")
						.append("                padding: 8px;\r\n").append("            }\r\n").append("        }\r\n")
						.append("    </style>\r\n").append("</head>\r\n").append("<body>\r\n").append("    <table>\r\n")
						.append("        <tr>\r\n").append("            <th>Name</th>\r\n")
						.append("            <th>Gmail</th>\r\n").append("            <th>Ph_No</th>\r\n")
						.append("            <th>Status</th>\r\n").append("        </tr>\r\n");

				while (rs.next()) {
					if (rs.getString("status").equals("INACTIVE")) {
						send.append("        <tr>\r\n").append("            <td>")
								.append(rs.getString("name").toString()).append("</td>\r\n").append("            <td>")
								.append(rs.getString("email")).append("</td>\r\n").append("            <td>")
								.append(rs.getString("phone_Numer")).append("</td>\r\n")
								.append("            <td>Deactivated</td>\r\n").append("        </tr>\r\n");
					}
					// System.out.println(rs.getString("phone_Numer"));
				}

				send.append("    </table>\r\n").append("</body>\r\n").append("</html>\r\n");

				String finalHtml = send.toString();
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("All 'Inactivated' Account");
				message.setContent(finalHtml, "text/html");
				Transport.send(message);

				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("message sent successfully...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
