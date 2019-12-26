# MojAuth

Java Wrapper for [Mojang's Authentication Service](https://wiki.vg/Authentication)

### Example Usages

Request an access token
````java
String username = // your mojang account email
String password = // your mojang account password
AuthAgent authAgent = new AuthAgent(Game.MINECRAFT, 1); // optional
String clientToken = // your client token (optional)
boolean requestUser = // defaults to false, sends an additional user
                      // object as response if true

MojAuth.authenticate(authAgent, username, password, clientToken,
                        requestUser); // returns a Response Object, which lets
                                      // you handle errors and process the result
````

Validate an access token
````java
String accessToken = // your access token
String clientToken = // the same client token used before (optional)

MojAuth.validate(accessToken, clientToken); // returns true, if the access token
                                            // is valid, false otherwise
````

And much more...