import psycopg2
import smtplib
from email.mime.text import MIMEText

class OrderProcessor:
    def process_order(self, order: dict) -> None:
        if not order["items"]:
            raise ValueError("Order is empty")
        
        total = sum(item["price"] * item["quantity"] for item in order["items"])
        
        if order["customer_type"] == "Premium":
            total *= 0.9  # 10% discount
        
        # Save to database
        connection = psycopg2.connect(
            host="localhost",
            database="mydb",
            user="postgres",
            password="password"
        )
        # ... save order
        connection.close()
        
        # Send email
        smtp = smtplib.SMTP('smtp.gmail.com', 587)
        smtp.starttls()
        smtp.login('your-email@gmail.com', 'your-password')
        msg = MIMEText('Your order has been processed')
        msg['Subject'] = 'Order confirmed'
        msg['From'] = 'your-email@gmail.com'
        msg['To'] = 'customer@email.com'
        smtp.send_message(msg)
        smtp.quit()

