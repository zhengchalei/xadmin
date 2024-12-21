<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .form-container h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group button {
            background-color: #007bff;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .form-group button:hover {
            background-color: #0056b3;
        }

        .captcha-group {
            display: flex;
            align-items: center;
        }

        .captcha-group img {
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .captcha-group button {
            background-color: #6c757d;
            color: #fff;
            padding: 8px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .captcha-group button:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Reset Password</h2>
    <form id="reset-password-form">
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
        <div class="form-group">
            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div class="form-group">
            <button type="submit">Reset Password</button>
        </div>
    </form>
    <a href="/login.html">Back to Login</a>
</div>
<script>
    function sendEmailCode() {
        const email = document.getElementById('email').value;
        if (!email) {
            alert('Please enter a valid email address.');
            return;
        }

        fetch(`/api/auth/send-rest-password-email-code/<#noparse>${encodeURIComponent(email)}</#noparse>`, {
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


    document.getElementById('reset-password-form').addEventListener('submit', async (event) => {
        event.preventDefault();
        const email = document.getElementById('email').value;
        const captcha = document.getElementById('captcha').value;
        const newPassword = document.getElementById('newPassword').value;

        const payload = {
            email,
            captcha,
            newPassword
        };

        try {
            const response = await fetch('/api/auth/rest-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                alert('Password reset successful.');
                window.location.href = '/login';
            } else {
                const data = await response.json();
                alert(`Error: <#noparse>${data.errorMessage || 'Password reset failed.'}</#noparse>`);
            }
        } catch (error) {
            alert(`Error submitting reset request: <#noparse>${error.message || 'Password reset failed.'}</#noparse>`);
        }
    });
</script>
</body>
</html>
