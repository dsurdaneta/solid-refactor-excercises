class OrderProcessor {
    processOrder(order: Order): void {
        if (order.items.length === 0) {
            throw new Error("Order is empty");
        }
        
        let total = order.items.reduce((sum, item) => 
            sum + item.price * item.quantity, 0);
        
        if (order.customerType === "Premium") {
            total *= 0.9; // 10% discount
        }
        
        // Save to database
        const mysql = require('mysql2');
        const connection = mysql.createConnection({
            host: 'localhost',
            user: 'root',
            password: 'password',
            database: 'mydb'
        });
        connection.connect();
        // ... save order
        connection.end();
        
        // Send email
        const nodemailer = require('nodemailer');
        const transporter = nodemailer.createTransport({
            service: 'gmail',
            auth: {
                user: 'your-email@gmail.com',
                pass: 'your-password'
            }
        });
        transporter.sendMail({
            from: 'your-email@gmail.com',
            to: 'customer@email.com',
            subject: 'Order confirmed',
            text: 'Your order has been processed'
        });
    }
}

