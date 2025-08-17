<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AskDocs</title>
    <script src="https://unpkg.com/htmx.org@1.9.10"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f8;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }
        #permanentHeader {
        	position: fixed;
        	top: 0;
        	left: 0;
        	background-color: white;
        	padding: 0.75rem 1rem;
        	font-size: 1.1rem;
        	font-weight: bold;
        	color: #10a37f;
        	border-bottom-right-radius: 8px;
        	box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        	z-index: 1000;
        }

        .chat-container {
            flex: 1;
            overflow-y: auto;
            padding: 20px;
            display: flex;
            flex-direction: column;
            margin-top: 3rem;
        }

        .message {
            max-width: 75%;
            padding: 10px 15px;
            margin: 5px 0;
            border-radius: 10px;
            line-height: 1.4;
            word-wrap: break-word;
        }

        .user {
            align-self: flex-end;
            background-color: #e5e5ea; 
            color: #000;
            border-bottom-right-radius: 0;
        }

        .bot {
            align-self: flex-start;
            background-color: #F7F7F8; 
            color: #000;
            border-bottom-left-radius: 0;
        }

        .input-container {
            display: flex;
            padding: 15px;
            background-color: white;
            border-top: 1px solid #ddd;
        }

        .input-container input {
            flex: 1;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-right: 10px;
        }

        .input-container button {
            background-color: #10a37f;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        .input-container button:hover {
            background-color: #0e8d6b;
        }
        #welcomeHeader {
        	display: flex;
        	justify-content: center;
    		align-items: center;
    		height: 80vh;
    		text-align: center;
    		font-size: 2rem;
    		font-weight: bold;
    		color: #333;
    		background-color: #f7f7f8;
    		border-bottom: 1px solid #ddd;
    	}
    </style>
</head>
<body>
<div id="permanentHeader">
	AskDocs
</div>
<div id="welcomeHeader">
	Welcome to Your Personal AI - start typing below!
</div>

<div class="chat-container" id="chatBox">

</div>

<form class="input-container"
      hx-get="/chat"
      hx-trigger="submit, keyup[enter]"
      hx-target="#chatBox"
      hx-swap="beforeend"
      hx-include="[name='query']"
      hx-on::after-request="this.reset()">
    <input type="text" name="query" placeholder="Ask something..." required>
    <button type="submit" onclick="htmx.remove(document.getElementById('welcomeHeader'))">Send</button>
</form>


</body>
</html>
