public class OrderProcessor
{
    public void ProcessOrder(Order order)
    {
        if (order.Items.Count == 0)
            throw new Exception("Order is empty");
        
        decimal total = 0;
        foreach (var item in order.Items)
        {
            total += item.Price * item.Quantity;
        }
        
        if (order.CustomerType == "Premium")
        {
            total *= 0.9m; // 10% discount
        }
        
        // Save to database
        using (var connection = new SqlConnection("connection string"))
        {
            connection.Open();
            var command = new SqlCommand(
                "INSERT INTO Orders (CustomerId, Total, CustomerType) VALUES (@CustomerId, @Total, @CustomerType)",
                connection);
            command.Parameters.AddWithValue("@CustomerId", order.CustomerId);
            command.Parameters.AddWithValue("@Total", total);
            command.Parameters.AddWithValue("@CustomerType", order.CustomerType);
            command.ExecuteNonQuery();
        }
        
        // Send email
        var smtp = new SmtpClient();
        smtp.Send("customer@email.com", "Order confirmed", "Your order has been processed");
    }
}

