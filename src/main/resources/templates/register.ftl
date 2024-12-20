<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: #f4f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .register-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        .register-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .captcha-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .send-code-btn {
            background: #007BFF;
            color: #fff;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .send-code-btn:hover {
            background: #0056b3;
        }
        .register-button {
            background: #28a745;
            color: #fff;
            border: none;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
        }
        .register-button:hover {
            background: #218838;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Register</h2>
    <form id="register-form">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <div class="captcha-group">
                <input type="email" id="email" name="email" required>
                <button type="button" class="send-code-btn" onclick="sendEmailCode()">Send Email Code</button>
            </div>
        </div>
        <div class="form-group">
            <label for="captcha">Captcha</label>
            <input type="text" id="captcha" name="captcha" required placeholder="Enter captcha">
        </div>
        <button type="submit" class="register-button">Register</button>
    </form>
</div>
<script>
    function sendEmailCode() {
        const email = document.getElementById('email').value;
        if (!email) {
            alert('Please enter a valid email address.');
            return;
        }

        fetch(`/api/auth/send-register-email-code/<#noparse>${encodeURIComponent(email)}</#noparse>`, {
            method: 'POST',
        })
            .then(response => {
                if (response.ok) {
                    alert('Verification code sent to your email!');
                } else {
                    alert('Failed to send email code.');
                }
            })
            .catch(() => alert('Error occurred while sending email code.'));
    }

    document.getElementById('register-form').addEventListener('submit', function (e) {
        e.preventDefault();

        const data = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value,
            email: document.getElementById('email').value,
            captcha: document.getElementById('captcha').value,
        };

        fetch('/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('Registration successful!');
                    window.location.href = '/login';
                } else {
                    alert(result.message || 'Registration failed.');
                }
            })
            .catch(() => alert('Error occurred while registering.'));
    });
</script>
</body>
</html>
