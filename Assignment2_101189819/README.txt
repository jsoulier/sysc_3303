Author: Jaan Soulier
Student Number: 101189819

=== Requirements ===

- IntelliJ IDEA
- JDK
- PDF viewer

=== Setup Instructions ===

The server, host, and client entrypoints are under their respective files.
You will need to start the applications in the order server, host, client.
Follow the instructions:

1.  Open IntelliJ IDEA
2.  Click File > Open
3.  Enter the directory of Assignment2_101189819
4.  Open the project explorer
5.  Right click on src/Server
6.  Click "Run Server.main()"
7.  Right click on src/Host
8.  Click "Run Host.main()"
9.  Right click on src/Client
10. Click "Run Client.main()"

=== Files ===

Java:
  src/Client
   - Implementation of the client
   - Entrypoint of the client
  src/ClientPacket
   - For packing client packets
   - For unpacking client packets
  src/Config
   - Common definitions
   - Number of client packets
   - Packet lengths
   - Ports
   - IP addresses
  src/Host
   - Implementation of the host
   - Entrypoint of the host
  src/Request
   - Enumeration of the valid server requests
   - Responses from the server
  src/Server
   - Implementation of the server
   - Entrypoint of the server
  src/StringHelper
   - Common string conversions

PDFs:
  sequence_diagram
   - Interaction between client, host, and server
  client_class_diagram
   - Client class diagram
  host_class_diagram
   - Host class diagram
  server_class_diagram
   - Server class diagram
