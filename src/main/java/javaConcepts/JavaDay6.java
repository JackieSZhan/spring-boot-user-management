package javaConcepts;

/***
 *
 * TCP - three way handshake
 *
 *      client                          server
 *  SYN(seq = X)   ->                   Hi Server this client, I want to connect, my # of seq is X
 *       <-       (SYN-ACK (seq = Y, ack=X+1))       <- Hi Client, I acknowledge your seq X, My starting seq number is Y
 *  Hi server I acknowledge your seq Y -> ACK(ack=Y+1) ->  client will have connection with server
 *
 *
 *
 *
 *  TCP connection termination
 *  server will send FIN (finish) - I have no more date to send, I would like to close the connection.
 *
 *
 *          client                              server
 *                      <- FIN <- Hi Client, I am done sending data
 *                      <- ACK -            OK acknowledged (server may still be sending data)
 *                      <- FIN  -           I am also done sending data now
 *                      <- ACK -            Ok let's close this connection
 *
 *
 *  UDP protocol is connection less, lightweight transport protocol
 *  There is no handshake, no guarantee of delivery, No guaranteed message ordering
 *
 *
 *  Client -> just send message to Server
 *
 *
 *  DNS -  how d domain name becomes an IP address
 *         www.google.com -> 123.123.123.123
 *
 *    1 type : www.google.com into your browser
 *    2 browser checks its own cache system first
 *       2.1 if it already know the IP, then just go to this IP and open connection
 *    3 if not cached, your computer asks resolver
 *    4: the resolver ask ROOT DNS server
 *          who handles ".com" domains?
 *          root server replies -> as the .com TLD server
 *   5: the resolver ask the .com TLD
 *          how handles google.com
 *           TLD server replies ask google.com 's authorized name server
 *    6: the resolver ask the auth name server for google.com
 *      what is the IP address for www.google.com
 *     aut name server replies: it is 123.123.123.123
 *
 *   7: return IP to your browser and open connection.
 *
 *
 *
 *   #####HTTP######
 *   what is idempotent means?
 *   an operation is idempotent if performing it multiple times ahs the same result or effect as performing once.
 *   example: place an order -> you call this URL -> /order/123 -> hit three times  -> only one order will be placed,
 *
 *
 *   HTTP methods:
 *
 *   GET   retrieve a resource    it is idempotent
 *   POST   create a new resource   it is NOT idempotent
 *   PUT    replace a resource entirely with the given data  it is idempotent
 *   Path   partially update a resource     It is NOT idempotent
 *   Delete      delete a resource           It is idempotent
 *   Head
 *   Option
 *
 *
 *  HTTP message structure
 *
 *  A request message:
 *  three components: 1: request line -> method + path + HTTP version
 *                     2: headers: metadata about the request, like what is host, content-type
 *                     3:Body: data you will send to server.
 *
 *
 *  A response message:
 *      three components:
 *      1: status : HTTP version + HTTP status code
 *      2: Header: metadata
 *      3: response payload
 *
 *     HTTP status codes:
 *     1XX   information -> request received, still processing
 *     2XX     success -> the request was successfully received and understand, and accpted
 *     3XX   redirection -> further action needed to complete the request, example: you need to go somewhere else(new version of URL) to access our system
 *     4XX   Client Error -> something wrong with the request itself
 *     5XX   server Error -> the server failed to fulfill a valid request.
 *
 *
 *     the most commonly used codes:
 *     200 OK
 *     201 created
 *     204 not content
 *     301 moved
 *     404 not found
 *     405 method not allowed
 *     500 internal server error
 *     502 bad gateway
 *
 *
 *     Cookies and sessions
 *     HTTP is stateless
 *     what is stateless, the server treats every incoming request as completely idependent
 *     what is cookie?
 *     a cookie is a data that the server asks the browser to store
 *     the browser will automatically sends it back to server with every request
 *     Set-Cookie: sessionId=user123; HttpOnly
 *
 *     what is session
 *     it is server side storage that holds data about a specific user information
 *     session id is unique and stores session id on the server side
 *     where to store it?
 *     database, cache, in memory
 *
 *     how they work together
 *     1: user logs in
 *     2: server creates a session, stores (userid: 123, name: Matthew, role: trainer) server side
 *          and generates sessionID = "user123"
 *     3: server send message back to client with session id: Set-Cookie: sessionId=user123
 *     4: browser stores this cookie
 *     5: every future request from this browser will includes: cookie : sessionId = user123
 *     6; server receives sessionId = user123, looks up the session data. and knows this request
 *     is from user : Matthew.
 *
 *
 *     HTTPs
 *     client                                               server
 *     Hello, here are the encryption algorithms I support ->
 *              <- lets user this algorithm, here is my certificate (public key)
 *              client verifies certificate, generate a shared  secret key
 *              encrypts secret key with public key, and sends it over
 *              <--- now both side share the same secret key
 *              all further request is noe encrypted
 *
 *
 *
 *
 */

public class JavaDay6 {
}
