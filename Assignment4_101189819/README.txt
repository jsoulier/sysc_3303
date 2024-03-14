Author: Jaan Soulier
Student Number: 101189819

=== Requirements ===

- IntelliJ IDEA
- JDK
- PDF viewer

=== Setup Instructions ===

The server, host, and client entrypoints are under their respective files.
You will need to start the applications in the order host, client, server.
Follow the instructions:

1.  Open IntelliJ IDEA
2.  Click File > Open
3.  Enter the directory of Assignment2_101189819
4.  Open the project explorer
5.  Right click on src/Host
6.  Click "Run Host.main()"
7.  Right click on src/Client
8.  Click "Run Client.main()"
9.  Right click on src/Server
10. Click "Run Server.main()"

=== Files ===

Java:
  src/Client: Client implementation and entrypoint
  src/Config: Common definitions
  src/Host: Host implementation and entrypoint
  src/HostInterface: Host RMI interface
  src/Packet: Datagram handling
  src/Request: Datagram requests
  src/Server: Server implementation and entrypoint

PDFs:
  class_diagram: Diagram for the applications

=== Questions ===

1. Why did I suggest that you use more than one thread for the implementation of the intermediate task?

While not used in my submission, two threads in the intermediate host could prove useful
to handle RPC for both the client and the server at the same time.

In my submission, it would mean having the two threads receive on the clientSocket and
serverSocket variables, rather then waiting on the sockets in a specific order.

2. Is it necessary to use synchronized in the intermediate task?

In my submission, since there is only one thread in the intermediate host and it is the
only thread accessing the datagram variables, synchronized is not required.

If I used two threads, synchronized would be required since both threads would need access
to the clientDatagram and serverDatagram variables.
