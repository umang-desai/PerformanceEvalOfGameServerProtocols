Performance Evaluation of Game Server Protocols README.

Compile Server & Client:
javac *.java

Run Server:
java GameServer <port>

Run Client:
java GameClient <ServerIp> <ServerPort> <ClientName> <Port>

Commands & Instructions to use: 
- move 
move <UP/DOWN/LEFT/RIGHT> <steps>. e.g. move DOWN 3

- location
has no arguments.

- elements
has no arguments.


Extra Features:
- User can quit and resume game anytime. When he resumes, he will continue from where he left off.

- User overflow control. If a user is 5 steps away from the wall which is to his right. And he chooses to take 10 steps towards the wall then he will only take 5 steps instead of the intended 10. This is a rule which is assumed. 

- Takes care of case sensitive commands.