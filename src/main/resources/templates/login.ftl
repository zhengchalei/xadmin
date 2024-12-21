<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to bottom right, #eceff1, #90caf9);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        .login-container h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #333;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
        }

        .form-group input {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .captcha-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .captcha-group img {
            height: 40px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .refresh-button {
            background: none;
            border: none;
            cursor: pointer;
            color: #1e88e5;
            font-weight: bold;
        }

        .login-button {
            width: 100%;
            padding: 0.75rem;
            border: none;
            border-radius: 5px;
            background: #1e88e5;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
        }

        .login-button:hover {
            background: #1565c0;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form id="login-form">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="captcha">Captcha</label>
            <div class="captcha-group">
                <img id="captcha-img" alt="Captcha">
                <button type="button" class="refresh-button" onclick="refreshCaptcha()">Refresh</button>
            </div>
            <input type="text" id="captcha" name="captcha" required placeholder="Enter captcha">
        </div>
        <button type="button" class="login-button" onclick="submitLogin()">Login</button>
    </form>
    <#--  注册  -->
    <a href="/register.html">Register</a>
    <#--  忘记密码  -->
    <a href="/rest-password.html">Forgot Password</a>
</div>
<script>
    let captchaID = crypto.randomUUID();

    function refreshCaptcha() {
        const captchaImg = document.getElementById('captcha-img');
        captchaImg.src = `/api/auth/captcha/` + captchaID;
    }

    refreshCaptcha();

    // Handle login form submission using fetch
    async function submitLogin() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const captcha = document.getElementById('captcha').value;

        // Validate form fields
        if (!username || !password || !captcha) {
            alert('Please fill in all required fields.');
            return;
        }

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                    captcha: captcha,
                    captchaID: captchaID,
                }),
            });

            if (response.ok) {
                const data = await response.json();
                if (data.success) {
                    // 重定向到首页
                    window.location.href = '/';
                } else {
                    alert('Login failed: ' + data.errorMessage);
                }
            } else {
                const error = await response.json();
                alert('Login failed: ' + error.errorMessage);
            }
        } catch (err) {
            console.error('Error submitting login:', err);
            alert('An error occurred. Please try again.');
        }
    }
</script>
</body>
</html>
