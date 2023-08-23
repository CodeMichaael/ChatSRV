from flask import Flask, request
import discord
from directx import FileManager

app = Flask(__name__)
intents = discord.Intents.default()
intents.typing = False
intents.presences = False
client = discord.Client(intents=intents)
channel = discord.utils.get(guild.text_channels, name='general')
TOKEN = ""
e = FileManager()

msg_data = {}

@app.route('/chatsrv/post', methods=['POST'])
def handle_data():
    if request.is_json:
        data = request.get_json()

        for key, value in data.items():
            if key is not "extra_data" or "stats_msg":

                if channel:
                    await channel.send(f"{key} said: {value}")
                    msg_data[key] = value

            elif key is "extra_data":
                e.write_content("Extra data.txt", {"extra_data": value})
                return "Extra data retrieved."
            
            elif key is "stats_msg":
                e.write_content("Statistics Message.txt", {"stats_msg": value})
                return "Statistics Message retrieved."

@app.route('/chatsrv/search', methods=['GET'])
def handle_search():
    # The value for the search doesn't really matter what it's searching 
    if request.is_json:
        data = request.get_json()

        for key, value in data.items():
            if key in msg_data:
                return value

client.run(TOKEN)

if __name__ == "__main__":
    app.run(debug=True)