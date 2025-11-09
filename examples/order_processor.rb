require 'pg'
require 'net/smtp'
require 'mail'

class OrderProcessor
  def process_order(order)
    raise "Order is empty" if order[:items].empty?
    
    total = order[:items].sum { |item| item[:price] * item[:quantity] }
    
    if order[:customer_type] == "Premium"
      total *= 0.9  # 10% discount
    end
    
    # Save to database
    connection = PG.connect(
      host: 'localhost',
      dbname: 'mydb',
      user: 'postgres',
      password: 'password'
    )
    connection.exec_params(
      "INSERT INTO Orders (CustomerId, Total, CustomerType) VALUES ($1, $2, $3)",
      [order[:customer_id], total, order[:customer_type]]
    )
    connection.close
    
    # Send email
    mail = Mail.new do
      from    'your-email@gmail.com'
      to      'customer@email.com'
      subject 'Order confirmed'
      body    'Your order has been processed'
    end
    
    mail.delivery_method :smtp, {
      address: 'smtp.gmail.com',
      port: 587,
      domain: 'gmail.com',
      user_name: 'your-email@gmail.com',
      password: 'your-password',
      authentication: 'plain',
      enable_starttls_auto: true
    }
    
    mail.deliver!
  end
end

