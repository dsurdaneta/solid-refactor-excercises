import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class OrderProcessor {
    public void processOrder(Order order) throws Exception {
        if (order.getItems().isEmpty()) {
            throw new Exception("Order is empty");
        }
        
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        
        if ("Premium".equals(order.getCustomerType())) {
            total *= 0.9; // 10% discount
        }
        
        // Save to database
        String connectionString = "jdbc:sqlserver://localhost;databaseName=mydb;user=sa;password=password";
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            String sql = "INSERT INTO Orders (CustomerId, Total, CustomerType) VALUES (?, ?, ?)";
            try (PreparedStatement command = connection.prepareStatement(sql)) {
                command.setInt(1, order.getCustomerId());
                command.setDouble(2, total);
                command.setString(3, order.getCustomerType());
                command.executeUpdate();
            }
        }
        
        // Send email
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("your-email@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("customer@email.com"));
        message.setSubject("Order confirmed");
        message.setText("Your order has been processed");
        
        Transport.send(message);
    }
}

